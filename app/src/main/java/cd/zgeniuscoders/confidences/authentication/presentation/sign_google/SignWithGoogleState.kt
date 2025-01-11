package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

data class SignWithGoogleState(
    var isLogin: Boolean = false,
    val isLogged: Boolean = false,
    var message: String = ""
)
