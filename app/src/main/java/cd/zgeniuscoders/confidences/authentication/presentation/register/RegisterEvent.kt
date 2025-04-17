package cd.zgeniuscoders.confidences.authentication.presentation.register

import cd.zgeniuscoders.confidences.authentication.presentation.login.LoginEvent

sealed interface RegisterEvent {
    data class OnEmailChange(val email: String) : RegisterEvent
    data class OnUsernameChange(val username: String) : RegisterEvent
    data class OnPasswordChange(val password: String) : RegisterEvent
    data object OnSubmit : RegisterEvent
}