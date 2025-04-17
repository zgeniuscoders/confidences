package cd.zgeniuscoders.confidences.authentication.presentation.sign_google

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.authentication.presentation.components.OrDivider
import cd.zgeniuscoders.confidences.authentication.presentation.login.LoginEvent
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

        if (state.canPass) {
            if (state.isLogged && state.hasAccount) {
                navController.navigate(Routes.MainNavGraph)
            } else {
                navController.navigate(Routes.OnBoarding)
            }
        }

    }

    SignWithGoogleBody(
        navController = navController,
        state = state,
        onEvent = onEvent
    )

}

@Composable
fun SignWithGoogleBody(
    state: SignWithGoogleState, onEvent: (event: SignWithGoogleEvent) -> Unit,
    navController: NavHostController
) {

    Scaffold { innerP ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = innerP.calculateTopPadding())
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                "Connectez-vous ou créez un compte sur Confidence",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 20.dp)
            )

            Box(Modifier.height(10.dp))

            Column {
                TextField(
                    shape = RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp),
                    value = state.email,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Email")
                    },
                    onValueChange = {
                        onEvent(SignWithGoogleEvent.OnEmailChange(it))
                    }
                )
                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp),
                    value = state.password,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Mot de passe")
                    },
                    onValueChange = {
                        onEvent(SignWithGoogleEvent.OnPasswordChange(it))
                    }
                )
                Box(Modifier.height(10.dp))
                Button(
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(SignWithGoogleEvent.OnSubmit)
                    }
                ) {
                    Text("Se connecter")
                }
                Button(
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(Routes.Register)
                    }
                ) {
                    Text("Cree un  compte")
                }
            }

            OrDivider()

            Button(
                shape = RoundedCornerShape(5.dp),
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
                SignWithGoogleBody(SignWithGoogleState(), {}, rememberNavController())
            }
        }
    }
}