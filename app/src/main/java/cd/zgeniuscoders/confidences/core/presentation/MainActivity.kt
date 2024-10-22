package cd.zgeniuscoders.confidences.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignGoogleScreen
import cd.zgeniuscoders.confidences.onboarding.presentation.PhoneNumberPage
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConfidencesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = innerPadding.calculateTopPadding())
                            .padding(horizontal = 10.dp)
                    ) {
                        PhoneNumberPage()
                    }
                }
            }
        }
    }
}

