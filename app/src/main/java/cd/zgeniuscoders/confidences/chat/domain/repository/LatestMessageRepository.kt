package cd.zgeniuscoders.confidences.chat.domain.repository

import cd.zgeniuscoders.confidences.chat.data.dto.LatestMessageDto
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessageRequest
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface LatestMessageRepository {

    suspend fun getLatestMessage(userId: String): Flow<Result<LatestMessageDto>>

    suspend fun saveLatestMessage(
        userId: String,
        request: LatestMessageRequest
    ): Result<Boolean>

}