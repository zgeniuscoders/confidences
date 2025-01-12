package cd.zgeniuscoders.confidences.chat.data.mappers

import cd.zgeniuscoders.confidences.chat.data.dto.MessageDto
import cd.zgeniuscoders.confidences.chat.data.dto.MessagesDto
import cd.zgeniuscoders.confidences.chat.domain.models.Message

fun MessagesDto.toMessageList(): List<Message> {
    return data.map {
        Message(
            id = it.id,
            message = it.message,
            image = it.image,
            timestamp = it.sendAt,
            senderId = it.senderId
        )
    }
}

fun MessageDto.toMessage(): Message {
    return Message(
        id = data.id,
        message = data.message,
        image = data.image,
        timestamp = data.sendAt,
        senderId = data.senderId
    )
}