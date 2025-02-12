package cd.zgeniuscoders.confidences.chat.data

import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage

fun LatestMessage.isChatOwner(userId: String): Boolean {
    return room === userId
}