package cd.zgeniuscoders.confidences.user.domain.models

data class User(
    val userId: String,
    val phoneNumber: String,
    val username: String?,
    val profilePictureUrl: String?,
    val email: String?
)