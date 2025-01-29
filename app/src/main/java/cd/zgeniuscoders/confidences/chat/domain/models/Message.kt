package cd.zgeniuscoders.confidences.chat.domain.models

data class Message(
    val id: String,
    var message: String,
    val senderId: String,
    val image: String?,
    val timeAgo: String,
    val timestamp: Long,
    var isRead: Boolean
)
