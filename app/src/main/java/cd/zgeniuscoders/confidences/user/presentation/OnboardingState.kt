package cd.zgeniuscoders.confidences.user.presentation

data class OnboardingState(
    var username: String = "",
    var phoneNumber: String = "",
    var isLoading: Boolean = false,
    var currentIndex: Int = 1,
    var totalIndex: Int = 5,
    var message: String = "",
    var hasAccount: Boolean = false
)
