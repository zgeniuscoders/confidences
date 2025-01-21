package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.chat.data.mappers.toContactList
import cd.zgeniuscoders.confidences.chat.data.mappers.toLatsMessageList
import cd.zgeniuscoders.confidences.chat.data.services.ContactService
import cd.zgeniuscoders.confidences.chat.domain.models.Contact
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage
import cd.zgeniuscoders.confidences.chat.domain.repository.LatestMessageRepository
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.mappers.toUserList
import cd.zgeniuscoders.confidences.user.data.mappers.toUserModel
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val latestMessageRepository: LatestMessageRepository,
    private val contactService: ContactService,
    private val userRepository: UserRepository,
    private val currentUser: FirebaseAuth
) : ViewModel() {

    val user = currentUser.currentUser

    private var _state = MutableStateFlow(ChatListState())
    val state = combine(
        contactService.contactLists,
        _state
    ) { contactLists, state ->
        state.copy(
            contacts = contactLists.toContactList()
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    init {
        getCurrentUser()
        getContacts()
        getMessages()
    }


    fun onTriggerEvent(event: ChatListEvent){

    }

    private fun getMessages(){
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

                            val latestMessages = res.data!!.toLatsMessageList()

                            getUsers(latestMessages)

                        }
                    }

                }
                .launchIn(viewModelScope)
        }
    }

    private fun getUsers(latestMessages: List<LatestMessage>) {

        viewModelScope.launch {

            val usersIds = latestMessages.map { it.receiverId }

            userRepository
                .getUsersByIds(usersIds)
                .onEach { res ->

                    when (res) {
                        is Result.Error -> {
                            _state.update {
                                it.copy(isLoading = false, message = res.message.toString())
                            }
                        }

                        is Result.Success -> {

                            val users = res.data!!.toUserList()

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    messages = latestMessages,
                                    users = users
                                )
                            }
                        }
                    }

                }
                .launchIn(viewModelScope)


        }


    }

    private fun getCurrentUser() {
        viewModelScope.launch {

            _state.update {
                it.copy(message = "")
            }

            userRepository
                .getUserById(user!!.uid)
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
                                it.copy(currentUser = user)
                            }
                        }
                    }


                }.launchIn(viewModelScope)

        }
    }

    private fun getContacts(){
        viewModelScope.launch {
            contactService
                .fetchContact()
                .onEach { res ->
                    when (res) {
                        is Result.Error -> {
                            _state.update {
                                it.copy(
                                    message = it.message.toString()
                                )
                            }
                        }

                        is Result.Success -> {

                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

}