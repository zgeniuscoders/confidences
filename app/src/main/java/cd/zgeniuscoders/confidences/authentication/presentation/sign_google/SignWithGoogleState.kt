package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

data class SignWithGoogleState(
    var canPass: Boolean = false,
    val isLogged: Boolean = false,
    val hasAccount: Boolean = false,
    var message: String = ""
)
