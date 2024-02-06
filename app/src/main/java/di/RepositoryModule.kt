package di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.TicketRepositoryImpl
import repository.interfaces.TicketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTicketRepository(
        ticketRepository: TicketRepositoryImpl,
    ): TicketRepository
}