package cd.zgeniuscoders.confidences.chat.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ContactDto(
    val data: ContactDtoData,
    val error: String? = null
)

data class ContactsDto(
    val data: List<ContactDtoData>
)

@Serializable
data class ContactDtoData(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("phone_number")
    val numberPhone: String = "",
)