package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cd.zgeniuscoders.confidences.R

@Composable
fun SignGoogleScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Chats",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.confident_text),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "google icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text("Google")
            }
        }
    }
}

@Composable
@PreviewLightDark
fun SignGoogleScreenPreview(modifier: Modifier = Modifier) {
    Scaffold { innerP ->
        Column(
            modifier
                .padding(vertical = innerP.calculateTopPadding())
                .padding(horizontal = 10.dp)
        ) {
            SignGoogleScreen()
        }
    }
}