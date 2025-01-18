package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.chat.data.mappers.toLatsMessageList
import cd.zgeniuscoders.confidences.chat.domain.repository.LatestMessageRepository
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val latestMessageRepository: LatestMessageRepository,
    private val currentUser: FirebaseAuth
) : ViewModel() {

    val user = currentUser.currentUser

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
        Log.i("MESSAGE", "before getting message")
        viewModelScope.launch {

            _state.update {
                it.copy(message = "")
            }

            latestMessageRepository
                .getLatestMessage(user!!.uid)
                .onEach { res ->

                    when (res) {
                        is Result.Error -> {

                            _state.update {
                                it.copy(isLoading = false, message = res.message.toString())
                            }
                        }

                        is Result.Success -> {

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    messages = res.data!!.toLatsMessageList()
                                )
                            }

                        }
                    }

                }
                .launchIn(viewModelScope)
        }
    }

}