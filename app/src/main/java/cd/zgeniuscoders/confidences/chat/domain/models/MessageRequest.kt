package cd.zgeniuscoders.confidences.chat.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageRequest(
    @SerialName("id")
    var id: String = "",
    @SerialName("sender_id")
    val senderId: String = "",
    @SerialName("message")
    val message: String = "",
    @SerialName("image")
    val image: String? = null,
    @SerialName("timestamp")
    val sendAt: Long = 0,
    @SerialName("read")
    val read: Boolean = false,
)
