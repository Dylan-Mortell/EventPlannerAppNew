package ie.setu.donationx.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE EventModel ADD COLUMN latitude DOUBLE NOT NULL DEFAULT 0.0")
            database.execSQL("ALTER TABLE EventModel ADD COLUMN longitude DOUBLE NOT NULL DEFAULT 0.0")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "donation_x_database"
        )
            .addMigrations(MIGRATION_2_3)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDonationDAO(appDatabase: AppDatabase): DonationDAO =
        appDatabase.getDonationDAO()

    @Provides
    fun provideEventDAO(appDatabase: AppDatabase): EventDAO =
        appDatabase.getEventDAO()

    @Provides
    fun provideRoomRepository(
        donationDAO: DonationDAO,
        eventDAO: EventDAO
    ): RoomRepository =
        RoomRepository(donationDAO, eventDAO)
}

