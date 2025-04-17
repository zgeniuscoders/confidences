package cd.zgeniuscoders.confidences.authentication.presentation.login

sealed interface LoginEvent {

    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent

    data object OnSubmit: LoginEvent

}