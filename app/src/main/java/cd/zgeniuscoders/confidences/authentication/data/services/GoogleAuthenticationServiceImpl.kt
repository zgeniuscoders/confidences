package cd.zgeniuscoders.confidences.authentication.data.services

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.authentication.domain.models.AuthResponse
import cd.zgeniuscoders.confidences.authentication.domain.models.User
import cd.zgeniuscoders.confidences.authentication.domain.services.GoogleAuthenticationService
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID

class GoogleAuthenticationServiceImpl(
    private val context: Context
) : GoogleAuthenticationService {

    private val auth = FirebaseAuth.getInstance()

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val degiset = md.digest(bytes)

        return degiset.fold("") { str, it ->
            str + "%2x".format(it)
        }
    }

    private fun getGoogleIdOptions(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()
    }

    private fun createCredentialManager(googleIdOption: GetGoogleIdOption): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    override suspend fun signWithGoogle(): Flow<Result<AuthResponse>> = callbackFlow {

        val googleIdOptions = getGoogleIdOptions()

        val request = createCredentialManager(googleIdOptions)


        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                context,
                request
            )

            val credential = result.credential
            if (credential is CustomCredential) {

                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                    try {

                        val googleIdToken = GoogleIdTokenCredential
                            .createFrom(credential.data)


                        val firebaseCredential = GoogleAuthProvider
                            .getCredential(
                                googleIdToken.idToken,
                                null
                            )

                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Log.e("SIGN_WITH_GOOGLE", "user sign in")

                                    trySend(

                                        Result.Success(
                                            data = auth.currentUser?.run {
                                                User(
                                                    userId = uid,
                                                    username = displayName,
                                                    email = email,
                                                    phoneNumber = "",
                                                    profilePictureUrl = photoUrl?.toString()
                                                )
                                            }?.let { it1 -> AuthResponse(data = it1) }
                                        )
                                    )
                                } else {
                                    trySend(
                                        Result.Error(
                                            message = it.exception?.message ?: ""
                                        )
                                    )
                                }
                            }
                            .addOnFailureListener {
                                trySend(
                                    Result.Error(
                                        message = it.message ?: ""
                                    )
                                )
                            }

                    } catch (e: GoogleIdTokenParsingException) {

                        trySend(
                            Result.Error(
                                message = e.message.toString()
                            )
                        )

                    }
                }
            }
        } catch (e: Exception) {
            trySend(
                Result.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }

}