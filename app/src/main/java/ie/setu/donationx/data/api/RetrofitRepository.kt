package ie.setu.donationx.data.api

import ie.setu.donationx.data.DonationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitRepository @Inject
constructor(private val serviceApi: DonationService)  {

    suspend fun getAll(): List<DonationModel>
    {
        return withContext(Dispatchers.IO) {
            val donations = serviceApi.getall()
            donations.body() ?: emptyList()
        }
    }

    suspend fun get(id: String): List<DonationModel>
    {
        return withContext(Dispatchers.IO) {
            val donation = serviceApi.get(id)
            donation.body() ?: emptyList()
        }
    }

    suspend fun insert(donation: DonationModel) : DonationWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.post(donation)
            wrapper
        }
    }

    suspend fun update(donation: DonationModel) : DonationWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.put(donation._id,donation)
            wrapper
        }
    }

    suspend fun delete(donation: DonationModel) : DonationWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.delete(donation._id)
            wrapper
        }
    }
}