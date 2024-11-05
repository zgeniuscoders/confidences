package cd.zgeniuscoders.confidences.onboarding.presentation

data class OnboardingState(
    var username: String = "",
    var phoneNumber: String = "",
    var isLoading: Boolean = false,
    var currentIndex: Int = 0,
    var totalIndex: Int = 3
)
