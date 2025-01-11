package cd.zgeniuscoders.confidences.authentication.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("photo_url")
    val photoUrl: String? = null
)