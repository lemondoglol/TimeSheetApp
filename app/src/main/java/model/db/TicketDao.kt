package model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticketEntity: TicketEntity)

    @Query("Select * From $TICKET_TABLE where ticketClosedTime is not null")
    suspend fun getAllTickets(): List<TicketEntity>

    @Query("Delete From $TICKET_TABLE")
    suspend fun clearTable()

    @Query("Select * From $TICKET_TABLE where repairOrderNumber=:repairOrderNumber")
    suspend fun getTicket(repairOrderNumber: String): TicketEntity?
}
