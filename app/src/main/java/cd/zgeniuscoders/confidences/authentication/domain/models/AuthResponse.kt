package cd.zgeniuscoders.confidences.authentication.domain.models

import cd.zgeniuscoders.confidences.user.domain.models.User

data class AuthResponse(
    val data: User
)