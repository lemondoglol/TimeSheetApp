package timesheetapp.summary

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import repository.interfaces.TicketRepository
import util.convertLongToYearMonthDay
import javax.inject.Inject

@HiltViewModel
class SummaryFragmentViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
) : ViewModel() {
    internal var currentReport = mutableStateOf("")
        private set

    private var selectedStartDate = mutableStateOf(0L)

    private var selectedEndDate = mutableStateOf(System.currentTimeMillis())

    internal val searchResult = derivedStateOf {
        when {
            selectedStartDate.value != 0L -> {
                "${selectedStartDate.value.convertLongToYearMonthDay()} - ${selectedEndDate.value.convertLongToYearMonthDay()}"
            }
            else -> "Start Date - ${selectedEndDate.value.convertLongToYearMonthDay()}"
        }
    }

    private fun searchResult() {
        if (selectedStartDate.value != 0L && selectedEndDate.value != 0L) {
            viewModelScope.launch {
                currentReport.value = ticketRepository.getTicketsWithinRange(
                    startTime = selectedStartDate.value,
                    endTime = selectedEndDate.value,
                ).joinToString("\n\n").also {

                }
            }
        }
    }

    internal fun onSelectedStartDate(date: Long?) {
        date?.let {
            selectedStartDate.value = it
            searchResult()
        }
    }

    internal fun onSelectedEndDate(date: Long?) {
        date?.let {
            selectedEndDate.value = it
            searchResult()
        }
    }
}