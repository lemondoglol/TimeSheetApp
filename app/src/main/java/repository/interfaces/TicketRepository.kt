package repository.interfaces

import model.db.TicketEntity

interface TicketRepository {
    suspend fun insertTicket(ticketEntity: TicketEntity)
    suspend fun getAllTickets(): List<TicketEntity>
    suspend fun getTicket(ticketNumber: String): TicketEntity?
    suspend fun getTicketsWithinRange(startTime: Long, endTime: Long): List<TicketEntity>
}
