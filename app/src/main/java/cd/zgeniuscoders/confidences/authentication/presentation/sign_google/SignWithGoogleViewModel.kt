package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.authentication.domain.models.Login
import cd.zgeniuscoders.confidences.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.confidences.authentication.domain.services.GoogleAuthenticationService
import cd.zgeniuscoders.confidences.core.domain.models.Session
import cd.zgeniuscoders.confidences.core.domain.services.SessionService
import cd.zgeniuscoders.confidences.core.domain.utils.Constant
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.domain.models.User
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignWithGoogleViewModel(
    private val googleAuthService: GoogleAuthenticationService,
    private val authService: AuthenticationService,
    private val userRepository: UserRepository,
    private val sessionService: SessionService
) : ViewModel() {

    var state by mutableStateOf(SignWithGoogleState())
        private set

    fun onEventHandler(event: SignWithGoogleEvent) {
        when (event) {
            SignWithGoogleEvent.OnGoogleButtonPressed -> {
                signWithGoogle()
            }

            is SignWithGoogleEvent.OnEmailChange -> state = state.copy(email = event.email)
            is SignWithGoogleEvent.OnPasswordChange -> state = state.copy(password = event.password)
            SignWithGoogleEvent.OnSubmit -> onSubmit()
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

    private fun signWithGoogle() {
        viewModelScope.launch(Dispatchers.IO) {

            state = state.copy(message = "")
            val response = googleAuthService.signWithGoogle()


            response.onEach {

                when (it) {

                    is Result.Error -> {
                        state = state.copy(
                            message = it.message.toString(),
                            hasAccount = false,
                            isLogged = false,
                            canPass = false
                        )
                        Log.e("GOOGLE_AUTH", it.message.toString())
                    }

                    is Result.Success -> {

                        val loggedUser = it.data!!.data

                        state = state.copy(message = "")

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