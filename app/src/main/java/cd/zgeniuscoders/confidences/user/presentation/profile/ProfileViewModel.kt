package cd.zgeniuscoders.confidences.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.confidences.core.domain.models.Session
import cd.zgeniuscoders.confidences.core.domain.services.SessionService
import cd.zgeniuscoders.confidences.core.domain.utils.Constant
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.mappers.toUserModel
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionService: SessionService,
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository,
    private val userId: String?
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set


    init {
        getCurrentUser()
    }


    fun onTriggerEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLogoutButtonClick -> logout()
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            userRepository
                .getUserById(userId!!)
                .onEach { res ->
                    state = when (res) {
                        is Result.Error -> {
                            state.copy(message = res.message.toString())
                        }

                        is Result.Success -> {
                            state.copy(
                                user = res.data?.toUserModel()
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun logout() {
        viewModelScope.launch {

            state = state.copy(message = "")

            authenticationService
                .signOut()
                .onEach { res ->

                    when (res) {

                        is Result.Error -> {
                            state = state.copy(message = res.message.toString())
                        }

                        is Result.Success -> {

                            sessionService.add(
                                Session(isAuthenticated = false),
                                Constant.IS_AUTHENTICATED
                            )

                            state = state.copy(
                                isLogout = true
                            )
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }


}