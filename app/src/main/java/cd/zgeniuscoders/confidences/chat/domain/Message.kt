package cd.zgeniuscoders.confidences.chat.domain

data class Message(
    val id: String,
    var message: String?,
    val senderId: String?,
    val image: String?,
    val timestamp: Long
)
