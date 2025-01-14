package cd.zgeniuscoders.confidences.chat.presentation.contact_list

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.confidences.chat.data.mappers.toContactList
import cd.zgeniuscoders.confidences.chat.data.services.ContactService
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.mappers.toUserList
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val contactService: ContactService,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state = MutableStateFlow(ContactListState())
    val state = combine(
        contactService.contactLists,
        _state
    ) { contactLists, state ->
        state.copy(
            contacts = contactLists.toContactList()
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
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
                        getContacts()
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onTriggerEvent(event: ContactListEvent) {
        when (event) {

            else -> {}
        }
    }

    private fun getContacts() {
        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            state.onEach {

                userRepository
                    .getUsersByPhoneNumber(state.value.contacts)
                    .onEach { res ->

                        when (res) {
                            is Result.Error -> {
                                _state.update {
                                    it.copy(
                                        message = res.message.toString(),
                                        isLoading = false
                                    )
                                }
                            }

                            is Result.Success -> {

                                _state.update {
                                    it.copy(
                                        users = res.data!!.toUserList(),
                                        isLoading = false
                                    )
                                }

                            }
                        }

                    }.launchIn(viewModelScope)

            }.launchIn(viewModelScope)



        }
    }

}