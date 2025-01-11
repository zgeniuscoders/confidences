package cd.zgeniuscoders.confidences.user.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.authentication.domain.models.UserRequest
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val userRepository: UserRepository,
    private val currentUser: FirebaseAuth
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
            state = state.copy(currentIndex = state.currentIndex + 1)
        }
    }

    private fun onFinishButton() {
        viewModelScope.launch {
            val isValidated = validated()
            if (isValidated) {
                val user = currentUser.currentUser

                userRepository
                    .addUser(
                        UserRequest(
                            state.phoneNumber,
                            user!!.uid,
                            state.username,
                            user.email!!
                        )
                    ).onEach { res ->

                        state = when (res) {
                            is Result.Error -> {
                                state.copy(message = res.message.toString(), isLoading = false)
                            }

                            is Result.Success -> {
                                state.copy(isLoading = false, hasAccount = true)
                            }
                        }

                    }.launchIn(viewModelScope)
            } else {
                state = state.copy(message = "Veuillez remplir tout les champs")
            }

        }
    }

    private fun validated(): Boolean {
        return !(state.username.isBlank() && state.phoneNumber.isBlank())
    }

    private fun onPreviousButton() {
        if (state.currentIndex > 0) {
            state = state.copy(currentIndex = state.currentIndex - 1)
        }
    }

}