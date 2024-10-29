package cd.zgeniuscoders.confidences.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    shape: Shape = TextFieldDefaults.shape,
    keyboardType: KeyboardType,
    maxLines: Int = 1,
    content: @Composable () -> Unit,
    isPasswordField: Boolean = false,
    onValueChange: (value: String) -> Unit,
) {

    TextField(
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth(),
        value = textValue,
        onValueChange = { onValueChange(it) },
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = content,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        shape = shape,
        placeholder = {
            Text(label)
        }
    )
}

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TextFieldComponentPreview() {
    Scaffold {
        TextFieldComponent(
            textValue = "", label = "Email",
            keyboardType = KeyboardType.Email,
            content = {
                Icon(Icons.Filled.Email, contentDescription = null)
            },
            onValueChange = {

            },
            isPasswordField = true
        )
    }
}