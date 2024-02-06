package core

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutLinedNumberInputTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (Float) -> Unit,
    hint: String = "",
    initialValue: String = "",
    keyboardType: KeyboardType = KeyboardType.Decimal,
) {
    var text by remember { mutableStateOf(initialValue) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier,
        value = text,
        singleLine = true,
        onValueChange = {
            text = it
            runCatching {
                val res = it.replace("-","")
                    .replace(",","")
                    .replace(" ", "")
                when (res.isBlank()) {
                    true -> onValueChange(0f)
                    false -> onValueChange(res.toFloat())
                }
            }
        },
        label = {
            Text(
                maxLines = 2,
                text = label,
            )
        },
        placeholder = { Text(hint) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType,
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutLinedTextInputTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    initialValue: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var text by remember { mutableStateOf(initialValue) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier,
        value = text,
        singleLine = true,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = {
            Text(
                maxLines = 2,
                text = label,
            )
        },
        placeholder = { Text(hint) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType,
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}

@Composable
fun ReadOnlyOutLinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
) {
    OutlinedTextField(
        value = TextFieldValue(text),
        modifier = modifier,
        enabled = false,
        onValueChange = {},
        singleLine = true,
        readOnly = true,
        label = {
            Text(
                maxLines = 2,
                text = label,
            )
        },
    )
}

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    iconSize: Dp =  ButtonDefaults.IconSize,
    imageVector: ImageVector = Icons.Filled.Add,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = imageVector,
            contentDescription = null,
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text)
    }
}