package di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import model.db.AppRoomDatabase
import model.db.TicketDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideMemoryAppDatabase(context: Context): AppRoomDatabase =
        Room.databaseBuilder(         // build in method
            context,
            AppRoomDatabase::class.java,
            "ticket table"
        ).build()

    @Provides
    @Singleton
    internal fun provideTicketDao(
        appRoomDatabase: AppRoomDatabase,
    ): TicketDao = appRoomDatabase.ticketDao()
}