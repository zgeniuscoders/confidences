package cd.zgeniuscoders.confidences.onboarding.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cd.zgeniuscoders.confidences.onboarding.domain.usecases.OnboardingInteractor

class OnboardingViewModel(
    private val onBoarding: OnboardingInteractor
) : ViewModel() {

    var state by mutableStateOf(OnboardingState())
        private set

    fun onTriggerEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnFinishButtonClicked -> onFinishButton()
            OnboardingEvent.OnNextButtonClicked -> onNextButton()
            is OnboardingEvent.OnUsernameChanged -> state = state.copy(username = event.value)
            is OnboardingEvent.OnPhoneNumberChanged -> state = state.copy(phoneNumber = event.value)
            OnboardingEvent.OnPreviousButtonClicked -> onPreviousButton()
        }
    }

    private fun onNextButton() {
        if (state.currentIndex < state.totalIndex) {
            state = state.copy(currentIndex = state.currentIndex++)
        }
    }

    private fun onFinishButton() {

    }

    private fun onPreviousButton() {
        if (state.currentIndex > 0) {
            state = state.copy(currentIndex = state.currentIndex--)
        }
    }

}