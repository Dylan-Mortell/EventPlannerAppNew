package ie.setu.donationx.data.room

import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.EventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val donationDAO: DonationDAO,
    private val eventDAO: EventDAO
) {

    //  Donations
    fun getAllDonations(): Flow<List<DonationModel>> = donationDAO.getAll()

    fun getDonation(id: Int) = donationDAO.get(id)

    suspend fun insertDonation(donation: DonationModel) {
        donationDAO.insert(donation)
    }

    suspend fun updateDonation(donation: DonationModel) {
        donationDAO.update(donation.id, donation.message)
    }

    suspend fun deleteDonation(donation: DonationModel) {
        donationDAO.delete(donation)
    }

    // events
    fun getAllEvents(): Flow<List<EventModel>> = eventDAO.getAllEvents()

    fun getEvent(id: Int): Flow<EventModel> = eventDAO.getEventById(id)

    suspend fun insertEvent(event: EventModel) {
        eventDAO.insert(event)
    }

    suspend fun updateEvent(event: EventModel) {
        eventDAO.updateEvent(event)
    }
}
