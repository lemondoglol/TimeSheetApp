package core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import timesheetapp.R
import ui.Padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    modifier: Modifier = Modifier,
    text: String,
    datePickerState: DatePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
    ),
    onDateSelected: (Long?) -> Unit = {},
    // For access selected date
    // datePickerState.selectedDateMillis?.convertLongToYearMonthDay()
) {
    val showDialog = rememberSaveable { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                showDialog.value = true
            },
        ) {
            Text(text)
        }

        if (showDialog.value) {
            DatePickerDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog.value = false
                        onDateSelected(datePickerState.selectedDateMillis)
                    }) {
                        Text(stringResource(R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            ) {
                DatePicker(
                    modifier = Modifier.padding(Padding.paddingSmall),
                    state = datePickerState,
                )
            }
        }
    }
}
