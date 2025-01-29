package cd.zgeniuscoders.confidences.chat.presentation.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.confidences.chat.data.mappers.toMessageList
import cd.zgeniuscoders.confidences.chat.data.mappers.toMessageRequest
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
import java.util.UUID

class ChatViewModel(
    private val messageRepository: MessageRepository,
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val latestMessageRepository: LatestMessageRepository,
    private val currentUser: String?
) : ViewModel() {

    private val receiverId = savedStateHandle.toRoute<Routes.Chat>().userId
    private val receiverPhoneNumber = savedStateHandle.toRoute<Routes.Chat>().phoneNumber
    private val isSenderSentMessageFirst = savedStateHandle.toRoute<Routes.Chat>().isFirst

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
            is ChatEvent.OnDeleteMessageForMe -> deleteMessageForMe(event.messageId)
            is ChatEvent.OnDeleteMessageForEveryOne -> deleteMessageForEveryOne(event.messageId)
        }
    }

    private suspend fun deleteMessage(messageId: String, room: String) {

            _state.update {
                it.copy(error = "")
            }

            val res = messageRepository
                .deleteMessage(
                    room,
                    messageId
                )

            when (res) {
                is Result.Error -> {
                    _state.update {
                        it.copy(error = res.message.toString())
                    }
                }

                is Result.Success -> {

                }
            }

    }

    private fun deleteMessageForEveryOne(messageId: String) {
        viewModelScope.launch {
            deleteMessage(messageId, state.value.receiverRoom)
            deleteMessage(messageId, state.value.senderRoom)
        }
    }

    private fun deleteMessageForMe(messageId: String) {
        viewModelScope.launch {
            deleteMessage(messageId, state.value.senderRoom)
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
                                    senderRoom = user.userId + receiverId + receiverPhoneNumber,
                                    receiverRoom = receiverId + user.userId + receiverPhoneNumber
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

                            markAsRead()

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

    private fun saveLatestMessage(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _state.update {
                it.copy(error = "")
            }

            val room = if(isSenderSentMessageFirst) {
                state.value.currentUserId
            }else{
                receiverId
            }

            val receiverLastMsg = LatestMessageRequest(
                id = uuid,
                receiverId = currentUser!!,
                message = state.value.message,
                sendAt = time,
                room = room,
                isRead = false
            )

            val senderLastMsg = LatestMessageRequest(
                id = uuid,
                receiverId = receiverId,
                message = state.value.message,
                sendAt = time,
                room = room,
                isRead = true
            )

            latestMessageRepository
                .upsertLatestMessage(receiverId, state.value.receiverRoom, receiverLastMsg)

            latestMessageRepository
                .upsertLatestMessage(currentUser, state.value.senderRoom, senderLastMsg)

        }
    }

    private fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {

            val uuid = UUID.randomUUID().toString()

            _state.update {
                it.copy(error = "")
            }

            val receiverMessageObj = MessageRequest(
                id = uuid,
                senderId = currentUser!!,
                message = state.value.message,
                sendAt = time,
                read = false
            )

            val senderMessageObj = MessageRequest(
                id = uuid,
                senderId = currentUser!!,
                message = state.value.message,
                sendAt = time,
                read = false
            )

            messageRepository
                .sendMessage(state.value.senderRoom, senderMessageObj)

            messageRepository
                .sendMessage(state.value.receiverRoom, receiverMessageObj)

            saveLatestMessage(uuid)

            _state.update {
                it.copy(message = "")
            }

        }
    }

    private fun markAsRead() {
        viewModelScope.launch {

            if (isMessageOwner()) {

                val lastMessage = state.value.messages.last()
                lastMessage.isRead = true

                messageRepository
                    .updateMessage(
                        state.value.senderRoom,
                        lastMessage.toMessageRequest()
                    )

                messageRepository
                    .updateMessage(
                        state.value.receiverRoom,
                        lastMessage.toMessageRequest()
                    )


            }

        }
    }

    private fun isMessageOwner(): Boolean {
        val message = state.value.messages.last()
        return message.senderId != state.value.currentUserId
    }

}