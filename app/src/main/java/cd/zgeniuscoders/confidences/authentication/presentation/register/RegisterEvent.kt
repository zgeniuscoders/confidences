package cd.zgeniuscoders.confidences.authentication.presentation.register


sealed interface RegisterEvent {
    data class OnEmailChange(val email: String) : RegisterEvent
    data class OnUsernameChange(val username: String) : RegisterEvent
    data class OnPasswordChange(val password: String) : RegisterEvent
    data object OnSubmit : RegisterEvent
}