package ie.setu.donationx.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.donationx.data.EventModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDAO {
    @Insert
    suspend fun insert(event: EventModel)

    @Query("SELECT * FROM eventmodel")
    fun getAllEvents(): Flow<List<EventModel>>

    @Query("SELECT * FROM eventmodel WHERE id = :id")
    fun getEventById(id: Long): Flow<EventModel>

    @Update
    suspend fun updateEvent(event: EventModel)

    @Delete
    suspend fun delete(event: EventModel)

    @Query("DELETE FROM eventmodel WHERE id = :eventId")
    suspend fun deleteEventById(eventId: Long)
}

