package cd.zgeniuscoders.confidences.user.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.core.presentation.components.OnboardingCard
import cd.zgeniuscoders.confidences.core.presentation.components.OutlineTextFieldComponent
import cd.zgeniuscoders.confidences.user.presentation.onboarding.OnboardingEvent
import cd.zgeniuscoders.confidences.user.presentation.onboarding.OnboardingState
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme

@Composable
fun Username(
    state: OnboardingState,
    onEvent: (event: OnboardingEvent) -> Unit = {}
) {

    Column {

        OnboardingCard(
            title = R.string.enter_username,
            contentDescription = "user profile",
            icon = Icons.Default.Person,
            description = R.string.usernameinfo
        )

        OutlineTextFieldComponent(
            textValue = state.username,
            label = "Pseudonyme",
            keyboardType = KeyboardType.Text
        ) {
            onEvent(OnboardingEvent.OnUsernameChanged(it))
        }

    }
}

@PreviewLightDark
@Composable
fun Preview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        Scaffold { innerP ->
            Column(
                modifier
                    .padding(vertical = innerP.calculateTopPadding())
                    .padding(horizontal = 20.dp)
            ) {
                Username(
                    OnboardingState(),
                ){}
            }
        }
    }
}