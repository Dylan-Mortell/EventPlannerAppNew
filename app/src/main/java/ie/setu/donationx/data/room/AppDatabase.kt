package ie.setu.donationx.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.EventModel

@Database(
    entities = [DonationModel::class, EventModel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDonationDAO(): DonationDAO
    abstract fun getEventDAO(): EventDAO
}