package cd.zgeniuscoders.confidences.authentication.presentation.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    var canPass: Boolean = false,
    val isLogged: Boolean = false,
    val hasAccount: Boolean = false,
    var message: String = ""
)
