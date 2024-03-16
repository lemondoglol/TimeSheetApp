package timesheetapp.summary

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import model.db.TicketEntity
import repository.interfaces.TicketRepository
import util.convertLongToYearMonthDay
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class SummaryFragmentViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
) : ViewModel() {
    internal var currentReport = mutableStateOf("")
        private set

    private var selectedStartDate = mutableStateOf(0L)

    private var selectedEndDate = mutableStateOf(System.currentTimeMillis())

    private var searchResultList: MutableList<TicketEntity> = mutableListOf()

    internal val searchDateRange = derivedStateOf {
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
                searchResultList.clear()
                searchResultList.addAll(
                    ticketRepository.getTicketsWithinRange(
                        startTime = selectedStartDate.value,
                        endTime = selectedEndDate.value,
                    ).also {
                        val content = TicketEntity.TicketEntityHeader + "\n" + it.joinToString("\n")

                        val result = StringBuilder()
                        result.append("\nTotal Repair Orders: ${it.size} \n")
                        result.append("Total Body Labor Hours: ${it.sumOf { it.bodyLabor }} \n")
                        result.append("Total Mache Labor Hours: ${it.sumOf { it.machineLabor }} \n")
                        result.append("Details: \n")
                        result.append(content)

                        currentReport.value = result.toString()
                    }
                )
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