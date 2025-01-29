package cd.zgeniuscoders.confidences.chat.data.mappers

import cd.zgeniuscoders.confidences.chat.data.dto.MessageDto
import cd.zgeniuscoders.confidences.chat.data.dto.MessagesDto
import cd.zgeniuscoders.confidences.chat.data.getTimeAgo
import cd.zgeniuscoders.confidences.chat.domain.models.Message
import cd.zgeniuscoders.confidences.chat.domain.models.MessageRequest

fun MessagesDto.toMessageList(): List<Message> {
    return data.map {
        Message(
            id = it.id,
            message = it.message,
            image = it.image,
            timeAgo = getTimeAgo(it.sendAt),
            senderId = it.senderId,
            isRead = it.read,
            timestamp = it.sendAt
        )
    }
}

fun MessageDto.toMessage(): Message {
    return Message(
        id = data.id,
        message = data.message,
        image = data.image,
        timeAgo = getTimeAgo(data.sendAt),
        senderId = data.senderId,
        timestamp = data.sendAt,
        isRead = data.read
    )
}

fun Message.toMessageRequest(): MessageRequest {
    return MessageRequest(
        id = id,
        message = message,
        image = image,
        sendAt = timestamp,
        senderId = senderId,
        read = isRead
    )
}