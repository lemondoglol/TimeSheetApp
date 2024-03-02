package repository

import kotlinx.coroutines.withContext
import model.db.TicketDao
import model.db.TicketEntity
import repository.interfaces.TicketRepository
import timesheetapp.coroutine.DispatcherProvider
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val ticketDao: TicketDao,
) : TicketRepository {
    override suspend fun insertTicket(ticketEntity: TicketEntity) =
        withContext(dispatcherProvider.io()) {
            ticketDao.insertTicket(ticketEntity)
        }

    override suspend fun getAllTickets(): List<TicketEntity> =
        withContext(dispatcherProvider.io()) {
            ticketDao.getAllTickets()
        }

    override suspend fun getTicket(ticketNumber: String): TicketEntity? =
        withContext(dispatcherProvider.io()) {
            ticketDao.getTicket(ticketNumber)
        }

    override suspend fun getTicketsWithinRange(
        startTime: Long,
        endTime: Long,
    ): List<TicketEntity> = withContext(dispatcherProvider.io()) {
        ticketDao.getTicketsWithinPeriod(
            startTime = startTime,
            endTime = endTime,
        )
    }
}