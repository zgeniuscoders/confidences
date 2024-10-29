package cd.zgeniuscoders.confidences.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.R

@Composable
fun OnboardingCard(
    title: Int,
    contentDescription: String,
    icon: ImageVector,
    description: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Icon(
            icon, contentDescription = contentDescription,
            modifier = Modifier.size(100.dp)
        )

        Text(text = stringResource(id = title), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(description),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}