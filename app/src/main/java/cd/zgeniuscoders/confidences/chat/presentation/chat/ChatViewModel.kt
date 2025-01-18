package cd.zgeniuscoders.confidences.chat.presentation.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.confidences.chat.data.mappers.toMessageList
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessageRequest
import cd.zgeniuscoders.confidences.chat.domain.models.MessageRequest
import cd.zgeniuscoders.confidences.chat.domain.repository.LatestMessageRepository
import cd.zgeniuscoders.confidences.chat.domain.repository.MessageRepository
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.user.data.mappers.toUserModel
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ChatViewModel(
    private val messageRepository: MessageRepository,
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val latestMessageRepository: LatestMessageRepository,
    private val currentUser: String?
) : ViewModel() {

    private val receiverId = savedStateHandle.toRoute<Routes.Chat>().userId

    private val time = Date().time

    private var _state = MutableStateFlow(ChatState())
    val state = _state
        .onStart {
            getCurrentUser()
            getUser()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value
        )


    fun onTriggerEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnMessageFieldChange -> _state.update { it.copy(message = event.message) }
            ChatEvent.OnSendMessageButtonClick -> sendMessage()
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {

            _state.update {
                it.copy(message = "")
            }

            userRepository
                .getUserById(currentUser!!)
                .onEach { res ->

                    when (res) {

                        is Result.Error -> {
                            _state.update {
                                it.copy(message = res.message.toString())
                            }
                        }

                        is Result.Success -> {

                            val user = res.data!!.toUserModel()

                            _state.update {
                                it.copy(
                                    currentUserId = user.userId,
                                    senderRoom = user.userId + receiverId + user.phoneNumber,
                                    receiverRoom = receiverId + user.userId + user.phoneNumber
                                )
                            }

                            getMessage()
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }

    private fun getMessage() {

        viewModelScope.launch(Dispatchers.IO) {

            _state.update {
                it.copy(isLoading = true)
            }

            Log.i("ROOM", "sender room : $${state.value.senderRoom}")
            Log.i("ROOM", "receiver room : $${state.value.receiverRoom}")

            messageRepository
                .getMessages(state.value.senderRoom)
                .onEach { res ->

                    when (res) {
                        is Result.Error -> {
                            _state.update {
                                it.copy(isLoading = false, error = res.message.toString())
                            }
                        }

                        is Result.Success -> {
                            _state.update {
                                it.copy(messages = res.data!!.toMessageList(), isLoading = false)
                            }
                        }
                    }

                }.launchIn(viewModelScope)

        }
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository
                .getUserById(receiverId)
                .onEach { res ->

                    when (res) {
                        is Result.Error -> {
                            _state.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    user = res.data?.toUserModel()
                                )
                            }
                        }
                    }

                }
                .launchIn(viewModelScope)
        }
    }

    private fun saveLatestMessage() {
        viewModelScope.launch {

            val message = LatestMessageRequest(
                receiverId = receiverId,
                message = state.value.message,
                sendAt = time
            )

            latestMessageRepository
                .saveLatestMessage(receiverId, message)

            latestMessageRepository
                .saveLatestMessage(currentUser!!, message)
        }
    }

    private fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {

            val message = MessageRequest(
                senderId = currentUser!!,
                message = state.value.message,
                sendAt = time
            )

            messageRepository
                .sendMessage(state.value.senderRoom, message)

            messageRepository
                .sendMessage(state.value.receiverRoom, message)

            saveLatestMessage()

            _state.update {
                it.copy(message = "")
            }
        }
    }

}