package cd.zgeniuscoders.confidences.chat.domain.models

data class LatestMessage(
    val id: String,
    var message: String,
    val receiverId: String,
    val image: String?,
    val room: String,
    val timestamp: String,
    val isRead: Boolean
)
