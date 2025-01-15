package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.authentication.domain.services.GoogleAuthenticationService
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignWithGoogleViewModel(
    private val authenticationService: GoogleAuthenticationService,
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(SignWithGoogleState())
        private set

    fun onEventHandler(event: SignWithGoogleEvent) {
        when (event) {
            SignWithGoogleEvent.OnGoogleButtonPressed -> {
                signWithGoogle()
            }
        }
    }

    private fun signWithGoogle() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = authenticationService.signWithGoogle()

            response.onEach {

                state = when (it) {

                    is Result.Error -> {
                        state.copy(message = it.message.toString())
                    }

                    is Result.Success -> {
                        state.copy(isLogged = true)
                    }

                }
            }.launchIn(viewModelScope)

        }
    }

}