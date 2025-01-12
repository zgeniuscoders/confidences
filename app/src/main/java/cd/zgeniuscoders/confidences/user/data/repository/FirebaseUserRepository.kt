package cd.zgeniuscoders.confidences.user.data.repository

import cd.zgeniuscoders.confidences.authentication.domain.models.UserRequest
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.dto.UserDto
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class FirebaseUserRepository(
    private val db: FirebaseFirestore
) : UserRepository {

    private val collection = db.collection("users")

    override suspend fun addUser(request: UserRequest): Flow<Result<Boolean>> = callbackFlow {
        try {
            collection
                .document(request.userId)
                .set(request)
                .await()

            trySend(
                Result.Success(
                    true
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

    override suspend fun updateUser(): Flow<Result<Boolean>> = callbackFlow {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(receiverId: String): Flow<Result<UserDto>> {
        TODO("Not yet implemented")
    }
}