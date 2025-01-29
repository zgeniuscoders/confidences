package cd.zgeniuscoders.confidences.chat.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val data: MessageDtoData
)

data class MessagesDto(
    val data: List<MessageDtoData>
)

@Serializable
data class MessageDtoData(
    @SerialName("id")
    val id: String = "",

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
