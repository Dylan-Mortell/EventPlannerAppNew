package ie.setu.donationx.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.EventModel

@Database(
    entities = [DonationModel::class, EventModel::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDonationDAO(): DonationDAO
    abstract fun getEventDAO(): EventDAO


    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE EventModel ADD COLUMN latitude DOUBLE NOT NULL DEFAULT 0.0")
            database.execSQL("ALTER TABLE EventModel ADD COLUMN longitude DOUBLE NOT NULL DEFAULT 0.0")
        }
    }

    // Database instance
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "donation_x_database"
            )
                .addMigrations(MIGRATION_2_3)
                .build()
            INSTANCE = instance
            instance
        }
    }
}
