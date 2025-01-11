package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

sealed interface SignWithGoogleEvent {

    data object OnGoogleButtonPressed: SignWithGoogleEvent


}