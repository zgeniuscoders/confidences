package cd.zgeniuscoders.confidences.user.presentation.profile

import cd.zgeniuscoders.confidences.user.domain.models.User

data class ProfileState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val message: String = "",
    val user: User? = null
)
