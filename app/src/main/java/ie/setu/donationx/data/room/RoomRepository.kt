package ie.setu.donationx.data.room

import ie.setu.donationx.data.DonationModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject
constructor(private val donationDAO: DonationDAO) {
    fun getAll(): Flow<List<DonationModel>>
            = donationDAO.getAll()

    fun get(id: Int) = donationDAO.get(id)

    suspend fun insert(donation: DonationModel)
            { donationDAO.insert(donation) }

    suspend fun update(donation: DonationModel)
            { donationDAO.update(donation.id,donation.message) }

    suspend fun delete(donation: DonationModel)
            { donationDAO.delete(donation) }
}