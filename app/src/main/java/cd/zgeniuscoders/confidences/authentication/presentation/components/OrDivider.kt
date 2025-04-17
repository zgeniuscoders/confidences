package cd.zgeniuscoders.confidences.authentication.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OrDivider(
    modifier: Modifier = Modifier,
    text: String = "ou"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray
        )
    }
}