package cd.zgeniuscoders.confidences.authentication.presentation.phone_number

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PhoneNumberViewModel : ViewModel() {

    var state by mutableStateOf(PhoneNumberState())
        private set

    fun onTriggerEvent(event: PhoneNumberEvent){
        state = when(event){
            is PhoneNumberEvent.OnCountryChanged -> state.copy(selectedCountry = event.country)
            is PhoneNumberEvent.OnPhoneNumberChanged -> state.copy(phoneNumber = event.phoneNumber)
        }
    }

}