package timesheetapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.db.TicketEntity
import repository.interfaces.TicketRepository
import timesheetapp.coroutine.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val ticketRepository: TicketRepository,
) : ViewModel() {

    internal var oldRepairOrder = mutableStateOf(TicketEntity.TESTING_TICKET)
        private set

    internal var newRepairOrder = mutableStateOf(TicketEntity.TESTING_TICKET)
        private set

    internal fun insertTicket() {
        viewModelScope.launch {
            ticketRepository.insertTicket(newRepairOrder.value)
            oldRepairOrder.value = newRepairOrder.value
        }
    }

    internal fun closeTicket() {
        viewModelScope.launch {
            newRepairOrder.value = newRepairOrder.value.copy(
                ticketClosedTime = System.currentTimeMillis(),
            )
            ticketRepository.insertTicket(newRepairOrder.value)
            oldRepairOrder.value = newRepairOrder.value
        }
    }

    internal fun fetchTicket(repairOrderNumber: String) {
        viewModelScope.launch {
            ticketRepository.getTicket(repairOrderNumber)?.let {
                oldRepairOrder.value = it
                newRepairOrder.value = it
            } ?: kotlin.run {
                oldRepairOrder.value = TicketEntity.TESTING_TICKET
            }
        }
    }

    internal fun onRepairOrderValueChanged(value: String) {
        kotlin.runCatching {
            newRepairOrder.value = newRepairOrder.value.copy(
                repairOrderNumber = value.toFloat().toLong(),
            )
        }
    }

    internal fun onKeyTagValueChanged(value: String) {
        runBlocking {
            newRepairOrder.value = newRepairOrder.value.copy(
                keyTag = value.toLong(),
            )
        }
    }

    internal fun onBodyLaborValueChanged(value: String) {
        kotlin.runCatching {
            newRepairOrder.value = newRepairOrder.value.copy(
                bodyLabor = value.toDouble(),
            )
        }
    }

    internal fun onMachineLaborValueChanged(value: String) {
        newRepairOrder.value = newRepairOrder.value.copy(
            machineLabor = value.toDouble(),
        )
    }

    internal fun onVehicleIdentificationNumberValueChanged(value: String) {
        newRepairOrder.value = newRepairOrder.value.copy(
            vehicleIdentificationNumber = value,
        )
    }

    internal fun onCarYearValueChanged(value: String) {
        newRepairOrder.value = newRepairOrder.value.copy(
            carYear = value.toLong(),
        )
    }

    internal fun onCarModelValueChanged(value: String) {
        newRepairOrder.value = newRepairOrder.value.copy(
            carModel = value,
        )
    }

    internal fun onCarMakeValueChanged(value: String) {
        newRepairOrder.value = newRepairOrder.value.copy(
            carMake = value,
        )
    }

    internal fun onCarColorValueChanged(value: String) {
        newRepairOrder.value = newRepairOrder.value.copy(
            carColor = value,
        )
    }
}
