package cd.zgeniuscoders.confidences.chat.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestMessageDto(
    val data: List<LatestMessageDtoData>
)

@Serializable
data class LatestMessageDtoData(
    @SerialName("id")
    val id: String = "",
    @SerialName("receive_id")
    val receiverId: String = "",
    @SerialName("message")
    val message: String = "",
    @SerialName("timestap")
    val image: String? = null,
    @SerialName("room")
    val room: String = "",
    @SerialName("timestamp")
    val sendAt: Long = 0,
)