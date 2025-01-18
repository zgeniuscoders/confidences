package cd.zgeniuscoders.confidences.authentication.data.services

import cd.zgeniuscoders.confidences.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.confidences.core.domain.utils.Result
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

    override suspend fun getSignInUser() {
        TODO("Not yet implemented")
    }
}