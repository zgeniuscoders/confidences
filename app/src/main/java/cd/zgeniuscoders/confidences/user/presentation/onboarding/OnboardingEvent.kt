package cd.zgeniuscoders.confidences.user.presentation.onboarding

import network.chaintech.cmpcountrycodepicker.model.CountryDetails

sealed interface OnboardingEvent {

    data object OnNextButtonClicked : OnboardingEvent
    data object OnPreviousButtonClicked : OnboardingEvent
    data object OnFinishButtonClicked : OnboardingEvent

    data class OnUsernameChanged(val value: String) : OnboardingEvent
    data class OnCountryCodeChanged(val value: CountryDetails?) : OnboardingEvent
    data class OnPhoneNumberChanged(val value: String) : OnboardingEvent

}