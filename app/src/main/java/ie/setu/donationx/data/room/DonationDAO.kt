package ie.setu.donationx.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.donationx.data.DonationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DonationDAO {
    @Query("SELECT * FROM donationmodel")
    fun getAll(): Flow<List<DonationModel>>

    @Query("SELECT * FROM donationmodel WHERE id=:id")
    fun get(id: Int): Flow<DonationModel>

    @Insert
    suspend fun insert(donation: DonationModel)

    @Query("UPDATE donationmodel SET message=:message WHERE id = :id")
    suspend fun update(id: Int, message:String)

    @Delete
    suspend fun delete(donation: DonationModel)
}