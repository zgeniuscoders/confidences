package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignWithGooglePage(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<SignWithGoogleViewModel>()
    val state = vm.state
    val onEvent = vm::onEventHandler

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    LaunchedEffect(key1 = state.canPass) {

        if(state.canPass){
            if (state.isLogged && state.hasAccount) {
                navController.navigate(Routes.MainNavGraph)
            } else {
                navController.navigate(Routes.OnBoarding)
            }
        }

    }

    SignWithGoogleBody(
        state,
        onEvent
    )

}

@Composable
fun SignWithGoogleBody(state: SignWithGoogleState, onEvent: (event: SignWithGoogleEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Confidences",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 20.dp)
        )

        Text(
            text = stringResource(R.string.confident_text),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onEvent(SignWithGoogleEvent.OnGoogleButtonPressed) },
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
                Text("Continuer avec google")
            }
        }
    }
}

@Composable
@PreviewLightDark
fun SignGoogleScreenPreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        Scaffold { innerP ->
            Column(
                modifier
                    .padding(vertical = innerP.calculateTopPadding())
                    .padding(horizontal = 10.dp)
            ) {
                SignWithGoogleBody(SignWithGoogleState(), {})
            }
        }
    }
}