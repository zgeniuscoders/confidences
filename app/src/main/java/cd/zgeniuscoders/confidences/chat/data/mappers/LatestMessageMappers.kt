package cd.zgeniuscoders.confidences.chat.data.mappers

import cd.zgeniuscoders.confidences.chat.data.dto.LatestMessageDto
import cd.zgeniuscoders.confidences.chat.data.getTimeAgo
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage

fun LatestMessageDto.toLatsMessageList(): List<LatestMessage> {
    return data.map {
        LatestMessage(
            id = it.id,
            message = it.message,
            image = it.image,
            timestamp = getTimeAgo(it.sendAt),
            receiverId = it.receiverId,
            room = it.room,
            isRead = it.isRead
        )
    }
}
