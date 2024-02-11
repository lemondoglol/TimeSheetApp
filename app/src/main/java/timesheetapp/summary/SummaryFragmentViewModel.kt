package timesheetapp.summary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import repository.interfaces.TicketRepository
import javax.inject.Inject

@HiltViewModel
class SummaryFragmentViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
) : ViewModel() {
    internal var currentReport = mutableStateOf("")
        private set

    init {
        // testing only
        getReport()
    }

    internal fun getReport() {
        viewModelScope.launch {
            currentReport.value = ticketRepository.getAllTickets().joinToString("\n")
        }
    }
}