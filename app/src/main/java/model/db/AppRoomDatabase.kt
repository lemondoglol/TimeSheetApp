package model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TicketEntity::class
    ],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}