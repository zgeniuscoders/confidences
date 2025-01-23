package cd.zgeniuscoders.confidences.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.core.domain.services.SessionService
import cd.zgeniuscoders.confidences.core.domain.utils.Constant
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val sessionService: SessionService,
) : ViewModel() {

    private var _state = MutableStateFlow(AppState())
    val state = _state
        .onStart { checkIfIsLogged() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value
        )


    private fun checkIfIsLogged(){
        viewModelScope.launch {

            sessionService
                .get(Constant.IS_AUTHENTICATED)
                .onEach { res ->

                    _state.update {
                        it.copy(isLogged = res, keepCondition = false)
                    }

                }
                .launchIn(viewModelScope)
        }
    }


}