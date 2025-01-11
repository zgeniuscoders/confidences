package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ChatListViewModel : ViewModel() {

    private var _state = MutableStateFlow(ChatListState())
    val state = _state
        .onStart {
            getMessages()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onTriggerEvent(event: ChatListEvent){

    }

    private fun getMessages(){

    }

}