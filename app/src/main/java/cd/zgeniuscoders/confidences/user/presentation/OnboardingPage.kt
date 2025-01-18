package cd.zgeniuscoders.confidences.user.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import cd.zgeniuscoders.confidences.user.presentation.components.Finish
import cd.zgeniuscoders.confidences.user.presentation.components.PhoneNumber
import cd.zgeniuscoders.confidences.user.presentation.components.Privacy
import cd.zgeniuscoders.confidences.user.presentation.components.Username
import cd.zgeniuscoders.confidences.user.presentation.components.Welcome
import cd.zgeniuscoders.confidences.user.presentation.onboarding.OnboardingEvent
import cd.zgeniuscoders.confidences.user.presentation.onboarding.OnboardingState
import cd.zgeniuscoders.confidences.user.presentation.onboarding.OnboardingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingPage(navHostController: NavHostController, snackbarHostState: SnackbarHostState) {
    val vm = koinViewModel<OnboardingViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    LaunchedEffect(state.hasAccount) {
        if (state.hasAccount) {
            navHostController.navigate(Routes.MainNavGraph)
        }
    }

    OnBoardingBody(state = state, onEvent)
}

@Composable
fun OnBoardingBody(
    state: OnboardingState,
    onEvent: (event: OnboardingEvent) -> Unit = {}
) {
    Column {

        if(state.isLoading){
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            when (state.currentIndex) {
                1 -> Welcome()
                2 -> Username(state, onEvent)
                3 -> PhoneNumber(state, onEvent)
                4 -> Privacy()
                5 -> Finish()
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(

            ) {
                if (state.currentIndex >= 5) {
                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = { onEvent(OnboardingEvent.OnFinishButtonClicked) }) {
                        Text(text = stringResource(R.string.finish))
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if(state.currentIndex > 1){
                            Button(onClick = { onEvent(OnboardingEvent.OnPreviousButtonClicked) }) {
                                Text(text = stringResource(R.string.previous))
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = { onEvent(OnboardingEvent.OnNextButtonClicked) }) {
                            Text(text = stringResource(R.string.next))
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@PreviewLightDark
@Composable
fun OnBoardingPagePreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        Scaffold { innerP ->
            Column(
                modifier
                    .padding(vertical = innerP.calculateTopPadding())
            ) {
                OnBoardingBody(
                    OnboardingState(
                        currentIndex = 1
                    )
                )
            }
        }
    }
}