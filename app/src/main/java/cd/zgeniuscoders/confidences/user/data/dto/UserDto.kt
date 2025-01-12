package cd.zgeniuscoders.confidences.user.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UserDto(
    val data: UserDtoData
)


@Serializable
data class UserDtoData(
    @SerialName("user_id")
    val userId: String = "",
    @SerialName("phone_number")
    val phoneNumber: String = "",
    @SerialName("username")
    val username: String = "",
    @SerialName("profile_picture_url")
    val profilePictureUrl: String? = null,
    @SerialName("email")
    val email: String = ""
)