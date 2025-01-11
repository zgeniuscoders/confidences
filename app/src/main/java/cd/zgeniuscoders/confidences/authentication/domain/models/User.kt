package cd.zgeniuscoders.confidences.authentication.domain.models

data class User(
    val userId: String,
    val phoneNumber: String,
    val username: String?,
    val profilePictureUrl: String?,
    val email: String?
)