package cd.zgeniuscoders.confidences.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OutlineTextFieldComponent(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    shape: Shape = TextFieldDefaults.shape,
    keyboardType: KeyboardType,
    maxLines: Int = 1,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    isPasswordField: Boolean = false,
    onValueChange: (value: String) -> Unit,
) {
    OutlinedTextField(
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth(),
        value = textValue,
        onValueChange = { onValueChange(it) },
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        shape = shape,
        placeholder = {
            Text(label)
        }
    )
}