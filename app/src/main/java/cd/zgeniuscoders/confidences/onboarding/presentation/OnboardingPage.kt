package cd.zgeniuscoders.confidences.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.onboarding.presentation.components.Username
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme

@Composable
fun OnBoardingPage(
    state: OnboardingState,
    onEvent: (event: OnboardingEvent) -> Unit = {}
) {
    Column {

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
        Username()

        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { onEvent(OnboardingEvent.OnNextButtonClicked) }) {
                Text(text = "Suivant")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@PreviewLightDark
@Composable
fun preview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        Scaffold { innerP ->
            Column(
                modifier
                    .padding(vertical = innerP.calculateTopPadding())
                    .padding(horizontal = 20.dp)
            ) {
                OnBoardingPage(
                    OnboardingState(
                        currentIndex = 1
                    )
                )
            }
        }
    }
}