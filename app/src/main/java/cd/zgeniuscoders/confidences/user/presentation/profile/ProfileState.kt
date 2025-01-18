package cd.zgeniuscoders.confidences.user.presentation.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val message: String = ""

)
