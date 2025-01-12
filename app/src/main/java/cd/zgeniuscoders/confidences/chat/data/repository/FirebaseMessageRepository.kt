package cd.zgeniuscoders.confidences.chat.data.repository

import cd.zgeniuscoders.confidences.chat.data.dto.MessageDtoData
import cd.zgeniuscoders.confidences.chat.data.dto.MessagesDto
import cd.zgeniuscoders.confidences.chat.domain.models.MessageRequest
import cd.zgeniuscoders.confidences.chat.domain.repository.MessageRepository
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseMessageRepository(
    private val db: FirebaseFirestore
) : MessageRepository {

    private val collection = db.collection("chats")

    private fun getRoom(room: String): CollectionReference {
        return collection.document(room).collection("messages")
    }

    override suspend fun getMessages(room: String): Flow<Result<MessagesDto>> = callbackFlow {
        try {

            getRoom(room).orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Result.Error(error.message.toString())
                        )
                    }

                    if (value != null) {
                        val messages = value.toObjects(MessageDtoData::class.java)
                        Result.Success(
                            data = MessagesDto(
                                data = messages
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

    override suspend fun sendMessage(room: String, request: MessageRequest): Result<Boolean> {
        return try {
            val docId = collection.document().id
            request.id = docId

            getRoom(room).document(request.id).set(request).await()


            Result.Success(
                true
            )

        } catch (e: Exception) {

            return Result.Error(
                e.message.toString()
            )

        } catch (e: FirebaseFirestoreException) {
            return Result.Error(
                e.message.toString()
            )

        }

    }

    override suspend fun deleteMessage(room: String, messageId: String): Result<Boolean> {
        return try {
            getRoom(room).document(messageId).delete().await()


            Result.Success(true)

        } catch (e: Exception) {

            return Result.Error(e.message.toString())

        } catch (e: FirebaseFirestoreException) {

            return Result.Error(e.message.toString())

        }

    }

    override suspend fun updateMessage(
        room: String, request: MessageRequest
    ): Result<Boolean> {
        return try {
            getRoom(room).document(request.id).set(request).await()


            Result.Success(true)

        } catch (e: Exception) {

            return Result.Error(e.message.toString())

        } catch (e: FirebaseFirestoreException) {

            return Result.Error(e.message.toString())

        }


    }

}