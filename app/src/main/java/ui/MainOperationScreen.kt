package ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import core.OutLinedNumberInputTextField
import core.OutLinedTextInputTextField
import core.ReadOnlyOutLinedTextField
import model.db.TicketEntity
import timesheetapp.HomeFragmentViewModel
import timesheetapp.R

@Composable
internal fun TickerMainInformationSecion(
    modifier: Modifier = Modifier,
    ticketEntity: TicketEntity,
    viewModel: HomeFragmentViewModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = Padding.paddingSmallPlus,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.keyTag),
            oldInfo = ticketEntity.keyTag,
            keyboardType = KeyboardType.Decimal,
            onValueChange = viewModel::onKeyTagValueChanged,
        )

        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.bodyLabor),
            oldInfo = ticketEntity.bodyLabor.toString(),
            keyboardType = KeyboardType.Decimal,
            onValueChange = viewModel::onBodyLaborValueChanged,
        )

        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.machineLabor),
            oldInfo = ticketEntity.machineLabor.toString(),
            keyboardType = KeyboardType.Decimal,
            onValueChange = viewModel::onMachineLaborValueChanged,
        )

        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.vehicleIdentificationNumber),
            oldInfo = ticketEntity.vehicleIdentificationNumber,
            keyboardType = KeyboardType.Text,
            onValueChange = viewModel::onVehicleIdentificationNumberValueChanged,
        )

        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.carMake),
            oldInfo = ticketEntity.carMake,
            keyboardType = KeyboardType.Text,
            onValueChange = viewModel::onCarMakeValueChanged,
        )

        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.carModel),
            oldInfo = ticketEntity.carModel,
            keyboardType = KeyboardType.Text,
            onValueChange = viewModel::onCarModelValueChanged,
        )

        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.carYear),
            oldInfo = ticketEntity.carYear.toString(),
            keyboardType = KeyboardType.Decimal,
            onValueChange = viewModel::onCarYearValueChanged,
        )


        TicketInformationRow(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.carColor),
            oldInfo = ticketEntity.carColor,
            keyboardType = KeyboardType.Text,
            onValueChange = viewModel::onCarColorValueChanged,
        )
    }
}

@Composable
private fun TicketInformationRow(
    modifier: Modifier = Modifier,
    header: String,
    oldInfo: String,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ReadOnlyOutLinedTextField(
            modifier = modifier.weight(0.5f),
            label = header,
            text = oldInfo,
        )
        Spacer(modifier = Modifier.width(Padding.paddingMedium))
        when (keyboardType) {
            KeyboardType.Decimal -> {
                OutLinedNumberInputTextField(
                    modifier = modifier.weight(1f),
                    label = header,
                    hint = stringResource(R.string.enter_new_data),
                    keyboardType = keyboardType,
                    onValueChange = {
                        onValueChange(it.toString())
                    }
                )
           }
            KeyboardType.Text -> {
                OutLinedTextInputTextField(
                    modifier = modifier.weight(1f),
                    label = header,
                    hint = stringResource(R.string.enter_new_data),
                    keyboardType = keyboardType,
                    onValueChange = {
                        onValueChange(it)
                    }
                )
            }
        }
    }
}
