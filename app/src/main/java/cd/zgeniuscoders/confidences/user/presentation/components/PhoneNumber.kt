package cd.zgeniuscoders.confidences.user.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.user.presentation.OnboardingEvent
import cd.zgeniuscoders.confidences.user.presentation.OnboardingState
import network.chaintech.cmpcountrycodepicker.model.CountryDetails
import network.chaintech.cmpcountrycodepicker.ui.CountryPickerBasicTextField

@Composable
fun PhoneNumber(state: OnboardingState, onEvent: (event: OnboardingEvent) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        val selectedCountryState: MutableState<CountryDetails?> = remember {
            mutableStateOf(null)
        }

        Spacer(modifier = Modifier.height(60.dp))

        Icon(
            Icons.Default.Phone, contentDescription = "phone number icon",
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = stringResource(R.string.phone_number_text),
            textAlign = TextAlign.Center,
        )

        CountryPickerBasicTextField(
            mobileNumber = state.phoneNumber,
            defaultCountryCode = "cd",
            onMobileNumberChange = {
                onEvent(OnboardingEvent.OnPhoneNumberChanged(it))
            },
            onCountrySelected = {
                selectedCountryState.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            defaultPaddingValues = PaddingValues(6.dp),
            showCountryFlag = true,
            showCountryPhoneCode = true,
            showCountryName = false,
            showCountryCode = false,
            showArrowDropDown = true,
            spaceAfterCountryFlag = 8.dp,
            spaceAfterCountryPhoneCode = 6.dp,
            spaceAfterCountryName = 6.dp,
            spaceAfterCountryCode = 6.dp,
            label = {
                Text(text = stringResource(R.string.mobile_number))
            },
            focusedBorderThickness = 2.dp,
            unfocusedBorderThickness = 1.dp,
            verticalDividerColor = Color(0XFFDDDDDD),
            colors = OutlinedTextFieldDefaults.colors()
        )

    }
}