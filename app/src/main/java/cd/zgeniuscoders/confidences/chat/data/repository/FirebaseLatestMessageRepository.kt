package cd.zgeniuscoders.confidences.chat.data.repository

import cd.zgeniuscoders.confidences.chat.data.dto.LatestMessageDto
import cd.zgeniuscoders.confidences.chat.data.dto.LatestMessageDtoData
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessageRequest
import cd.zgeniuscoders.confidences.chat.domain.repository.LatestMessageRepository
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseLatestMessageRepository(
    private val db: FirebaseFirestore
) : LatestMessageRepository {

    private val collection = db.collection("users")

    override suspend fun getLatestMessage(userId: String): Flow<Result<LatestMessageDto>> =
        callbackFlow {
            try {
                collection
                    .document(userId)
                    .collection("latestMessage")
                    .orderBy("sendAt", Query.Direction.DESCENDING)
                    .addSnapshotListener { value, error ->

                        if (error != null) {
                            trySend(
                                Result.Error(error.message.toString())
                            )
                        }

                        if (value != null) {
                            val messages = value.toObjects(LatestMessageDtoData::class.java)
                            trySend(
                                Result.Success(
                                    data = LatestMessageDto(
                                        data = messages
                                    )
                                )
                            )
                        }
                    }
            } catch (e: Exception) {
                trySend(
                    Result.Error(e.message.toString())
                )
            } catch (e: FirebaseFirestoreException) {
                trySend(
                    Result.Error(e.message.toString())
                )
            }

            awaitClose()
        }

    override suspend fun saveLatestMessage(
        userId: String,
        request: LatestMessageRequest
    ): Result<Boolean> {
        return try {
            collection
                .document(userId)
                .collection("latestMessage")
                .document(request.receiverId)
                .set(request)
                .await()

            Result.Success(
                true
            )
        } catch (e: Exception) {

            return Result.Error(e.message.toString())

        } catch (e: FirebaseFirestoreException) {
            return Result.Error(e.message.toString())

        }

    }

}