package cd.zgeniuscoders.confidences.onboarding.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.R

@Composable
fun Welcome() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(text = "Bienvenue sur Confidences !", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = stringResource(id = R.string.welcome_txt), textAlign = TextAlign.Center)
    }
}

@PreviewLightDark
@Composable
fun WelcomePreview(modifier: Modifier = Modifier) {
    Welcome()
}