package cd.zgeniuscoders.confidences.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignGoogleScreen
import network.chaintech.cmpcountrycodepicker.model.CountryDetails
import network.chaintech.cmpcountrycodepicker.ui.CountryPickerBasicTextField

@Composable
fun PhoneNumberPage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        val selectedCountryState: MutableState<CountryDetails?> = remember {
            mutableStateOf(null)
        }
        var mobileNumber by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.Default.Phone, contentDescription = "phone number icon",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.phone_number_text),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        CountryPickerBasicTextField(
            mobileNumber = mobileNumber,
            defaultCountryCode = "cd",
            onMobileNumberChange = {
                mobileNumber = it
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

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Envoyer")
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}

@PreviewLightDark
@Composable
fun PhoneNumberPreview() {
    Scaffold { innerP ->
        Column(
            modifier = Modifier
                .padding(vertical = innerP.calculateTopPadding())
                .padding(horizontal = 10.dp)
        ) {
            PhoneNumberPage()
        }
    }
}