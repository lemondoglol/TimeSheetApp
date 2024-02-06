package model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TICKET_TABLE)
data class TicketEntity(
    @PrimaryKey val repairOrderNumber: Long,
    val keyTag: String = "N/A",
    val bodyLabor: Double = 0.0,
    val machineLabor: Double = 0.0,
    val ticketClosedTime: Long? = null,
    val vehicleIdentificationNumber: String = "N/A",
    val carYear: Long = -1L,
    val carModel: String = "N/A",
    val carMake: String = "N/A",
    val carColor: String = "N/A",
) {
    companion object {
        val TESTING_TICKET = TicketEntity(
            repairOrderNumber = -1,
        )
    }
}

const val TICKET_TABLE = "ticket_table"
