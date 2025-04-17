package cd.zgeniuscoders.confidences.authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.authentication.domain.models.Login
import cd.zgeniuscoders.confidences.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.confidences.core.domain.models.Session
import cd.zgeniuscoders.confidences.core.domain.services.SessionService
import cd.zgeniuscoders.confidences.core.domain.utils.Constant
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.domain.models.User
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    val authService: AuthenticationService,
    val userRepository: UserRepository,
    val sessionService: SessionService
) : ViewModel() {


    var state by mutableStateOf(LoginState())
        private set

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> state = state.copy(email = event.email)
            is LoginEvent.OnPasswordChange -> state = state.copy(password = event.password)
            LoginEvent.OnSubmit -> onSubmit()
        }
    }

    fun onSubmit() {
        state = state.copy(message = "")
        viewModelScope.launch {
            val data = Login(email = state.email, password = state.password)
            authService.login(data).onEach { res ->

                when (res) {
                    is Result.Error -> {
                        state = state.copy(
                            message = res.message.toString(),
                            hasAccount = false,
                            isLogged = false,
                            canPass = false
                        )
                    }

                    is Result.Success -> {
                        val loggedUser = res.data!!.data
                        checkIfHasAccount(loggedUser)
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun checkIfHasAccount(loggedUser: User) {
        viewModelScope.launch {

            userRepository
                .hasAccount(loggedUser.userId)
                .onEach { res ->

                    when (res) {
                        is Result.Error -> {
                            state = state.copy(
                                message = res.message.toString(),
                                hasAccount = false,
                                isLogged = false,
                                canPass = true
                            )
                        }

                        is Result.Success -> {
                            val exists = res.data

                            if (exists == true) {
                                sessionService.add(
                                    Session(isAuthenticated = true),
                                    Constant.IS_AUTHENTICATED
                                )
                            }

                            state = state.copy(
                                isLogged = true,
                                message = "",
                                hasAccount = exists!!,
                                canPass = true
                            )
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }


}