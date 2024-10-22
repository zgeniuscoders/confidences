package cd.zgeniuscoders.confidences.authentication.presentation.phone_number

sealed interface PhoneNumberEvent {

    data class OnPhoneNumberChanged(val phoneNumber: String) : PhoneNumberEvent
    data class OnCountryChanged(val country: String) : PhoneNumberEvent

}