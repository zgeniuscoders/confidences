package cd.zgeniuscoders.confidences.onboarding.presentation

sealed interface OnboardingEvent {

    data object OnNextButtonClicked : OnboardingEvent
    data object OnPreviousButtonClicked : OnboardingEvent
    data object OnFinishButtonClicked : OnboardingEvent

    data class OnUsernameChanged(val value: String) : OnboardingEvent
    data class OnPhoneNumberChanged(val value: String) : OnboardingEvent

}