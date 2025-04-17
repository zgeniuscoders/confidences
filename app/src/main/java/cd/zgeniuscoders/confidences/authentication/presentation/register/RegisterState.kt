package cd.zgeniuscoders.confidences.authentication.presentation.register

data class RegisterState(
    var email: String = "",
    var password: String = "",
    var username: String = "",
    var canPass: Boolean = false,
    val isLogged: Boolean = false,
    val hasAccount: Boolean = false,
    var message: String = ""
)
