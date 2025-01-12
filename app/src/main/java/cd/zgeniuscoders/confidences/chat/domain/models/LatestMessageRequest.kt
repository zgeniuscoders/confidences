package cd.zgeniuscoders.confidences.chat.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestMessageRequest(
    @SerialName("id")
    var id: String = "",
    @SerialName("receiver_id")
    val receiverId: String = "",
    @SerialName("message")
    val message: String = "",
    @SerialName("timestap")
    val image: String? = null,
    @SerialName("timestamp")
    val sendAt: Long = 0,
)
