package cd.zgeniuscoders.confidences.authentication.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cd.zgeniuscoders.confidences.authentication.domain.models.Register
import cd.zgeniuscoders.confidences.authentication.presentation.login.LoginEvent
import cd.zgeniuscoders.confidences.authentication.presentation.login.LoginState
import cd.zgeniuscoders.confidences.authentication.presentation.login.LoginViewModel
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterPage(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val vm = koinViewModel<RegisterViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent


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

    RegisterBody(
        onEvent,
        state,
        snackbarHostState
    )
}

@Composable
fun RegisterBody(
    onEvent: (event: RegisterEvent) -> Unit,
    state: RegisterState,
    snackbarHostState: SnackbarHostState
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerP ->
        Column(
            modifier = Modifier
                .padding(vertical = innerP.calculateTopPadding())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Connexion", fontSize = 24.sp)
            Box(modifier = Modifier.height(10.dp))
            TextField(
                value = state.email,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email")
                },
                onValueChange = {
                    onEvent(RegisterEvent.OnEmailChange(it))
                }
            )
            TextField(
                value = state.username,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Nom d'utilisateur")
                },
                onValueChange = {
                    onEvent(RegisterEvent.OnUsernameChange(it))
                }
            )
            TextField(
                value = state.password,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Mot de passe")
                },
                onValueChange = {
                    onEvent(RegisterEvent.OnPasswordChange(it))
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(RegisterEvent.OnSubmit)
                }
            ) {
                Text("Cree mon compte")
            }
        }
    }

}

@PreviewLightDark
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        RegisterBody(
            state = RegisterState(),
            onEvent = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
