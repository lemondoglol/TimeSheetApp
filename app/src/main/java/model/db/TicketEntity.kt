package model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import util.convertLongToYearMonthDay

@Entity(tableName = TICKET_TABLE)
data class TicketEntity(
    @PrimaryKey val repairOrderNumber: Long,
    val keyTag: Long = -1L,
    val bodyLabor: Double = 0.0,
    val machineLabor: Double = 0.0,
    val ticketClosedTime: Long? = null,
    val vehicleIdentificationNumber: String = "N/A",
    val carYear: Long = -1L,
    val carModel: String = "N/A",
    val carMake: String = "N/A",
    val carColor: String = "N/A",
) {
    override fun toString(): String {
        return repairOrderNumber.toString().padEnd(5) +
                bodyLabor.toString().padEnd(14) +
                machineLabor.toString().padEnd(17) +
                ticketClosedTime?.convertLongToYearMonthDay()
    }

    companion object {
        val TESTING_TICKET = TicketEntity(
            repairOrderNumber = -1,
        )
        val TicketEntityHeader = "" +
                "RO".padEnd(5) +
                "BodyLaborHrs".padEnd(13) +
                "MachineLaborHrs".padEnd(16) +
                "OrderCloseTime"
    }
}

const val TICKET_TABLE = "ticket_table"
