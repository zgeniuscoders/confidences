package cd.zgeniuscoders.confidences.chat.domain.repository

import cd.zgeniuscoders.confidences.chat.data.dto.MessagesDto
import cd.zgeniuscoders.confidences.chat.domain.models.MessageRequest
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun getMessages(room: String): Flow<Result<MessagesDto>>

    suspend fun sendMessage(room: String,request: MessageRequest): Result<Boolean>

    suspend fun deleteMessage(room: String, messageId: String): Result<Boolean>

    suspend fun updateMessage(room: String,request: MessageRequest): Result<Boolean>

}