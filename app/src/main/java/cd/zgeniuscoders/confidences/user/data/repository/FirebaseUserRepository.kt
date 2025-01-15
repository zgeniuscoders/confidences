package cd.zgeniuscoders.confidences.user.data.repository

import android.util.Log
import cd.zgeniuscoders.confidences.authentication.domain.models.UserRequest
import cd.zgeniuscoders.confidences.chat.domain.models.Contact
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.dto.UserDto
import cd.zgeniuscoders.confidences.user.data.dto.UserDtoData
import cd.zgeniuscoders.confidences.user.data.dto.UsersDto
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class FirebaseUserRepository(
    db: FirebaseFirestore
) : UserRepository {

    private val collection = db.collection("users")
    override suspend fun hasAccount(userId: String): Flow<Result<Boolean>> = callbackFlow {
        try {

            collection
                .document(userId)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Result.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        trySend(
                            Result.Success(
                                data = value.exists()
                            )
                        )
                    }

                }

        } catch (e: Exception) {
            e.printStackTrace()

            trySend(
                Result.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }

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

    override suspend fun getUserById(userId: String): Flow<Result<UserDto>> = callbackFlow {
        try {

            collection
                .document(userId)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Result.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        val user = value.toObject(UserDtoData::class.java)
                        if (user != null) {
                            trySend(
                                Result.Success(
                                    data = UserDto(
                                        data = user
                                    )
                                )
                            )
                        } else {
                            trySend(
                                Result.Error(
                                    message = "Une erreure inatendu se survenir lors de la recuperation de cette utilisateur"
                                )
                            )
                        }

                    }

                }

        } catch (e: Exception) {
            e.printStackTrace()
            trySend(
                Result.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }

    override suspend fun getUsersByPhoneNumber(phoneNumbers: List<Contact>): Flow<Result<UsersDto>> =
        callbackFlow {
            try {
                val numbers = phoneNumbers.map { it.numberPhone }

                Log.i("CONTACT_FIR", numbers.toString())

                collection
                    .whereIn("phoneNumber", numbers)
                    .addSnapshotListener { value, error ->

                        if (error != null) {
                            trySend(
                                Result.Error(
                                    message = error.message.toString()
                                )
                            )
                        }

                        if (value != null) {
                            val users = value.toObjects(UserDtoData::class.java)
                            Log.i("CONTACT", "firebase $users")
                            trySend(
                                Result.Success(
                                    data = UsersDto(
                                        data = users
                                    )
                                )
                            )
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                trySend(
                    Result.Error(
                        message = e.message.toString()
                    )
                )
            }

            awaitClose()
        }
}