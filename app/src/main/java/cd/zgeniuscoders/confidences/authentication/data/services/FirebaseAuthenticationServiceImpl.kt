package cd.zgeniuscoders.confidences.authentication.data.services

import cd.zgeniuscoders.confidences.authentication.domain.models.AuthResponse
import cd.zgeniuscoders.confidences.authentication.domain.models.Login
import cd.zgeniuscoders.confidences.authentication.domain.models.Register
import cd.zgeniuscoders.confidences.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseAuthenticationServiceImpl(
    private val auth: FirebaseAuth
) : AuthenticationService {
    override suspend fun signOut(): Flow<Result<Boolean>> = callbackFlow {

        try {

            auth
                .signOut()

            trySend(
                Result.Success(
                    data = true
                )
            )

        } catch (e: Exception) {

            trySend(
                Result.Error(
                    message = e.message.toString()
                )
            )

        }

        awaitClose()

    }

    override suspend fun login(data: Login): Flow<Result<AuthResponse>> = callbackFlow {
        auth.signInWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    Result.Success(
                        AuthResponse(
                            User(
                                userId = user?.uid ?: "",
                                username = "",
                                email = user?.email ?: data.email,
                                phoneNumber = "",
                                profilePictureUrl = null
                            )
                        )
                    )
                } else {
                    trySend(
                        Result.Error(
                            it.exception?.message ?: ""
                        )
                    )
                }
            }
        awaitClose()
    }

    override suspend fun register(data: Register): Flow<Result<AuthResponse>> = callbackFlow {
        auth.createUserWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    trySend(
                        Result.Success(
                            AuthResponse(
                                User(
                                    userId = user?.uid ?: "",
                                    username = data.username,
                                    email = user?.email ?: data.email,
                                    phoneNumber = "",
                                    profilePictureUrl = null
                                )
                            )
                        )
                    )
                } else {
                    trySend(
                        Result.Error(it.exception?.message ?: "")
                    )
                }
            }

        awaitClose()
    }

    override suspend fun getSignInUser() {
        TODO("Not yet implemented")
    }
}