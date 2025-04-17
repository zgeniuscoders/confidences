package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

sealed interface SignWithGoogleEvent {

    data object OnGoogleButtonPressed : SignWithGoogleEvent

    data class OnEmailChange(var email: String) : SignWithGoogleEvent
    data class OnPasswordChange(var password: String) : SignWithGoogleEvent

    data object OnSubmit : SignWithGoogleEvent
}