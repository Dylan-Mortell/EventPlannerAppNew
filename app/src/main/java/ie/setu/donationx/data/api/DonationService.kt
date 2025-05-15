package ie.setu.donationx.data.api

import ie.setu.donationx.data.DonationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DonationService {
    @GET(ServiceEndPoints.DONATIONS_ENDPOINT)
    suspend fun getall(): Response<List<DonationModel>>

    @GET(ServiceEndPoints.DONATIONS_ENDPOINT + "/{id}")
    suspend fun get(@Path("id") id: String): Response<List<DonationModel>>

    @DELETE(ServiceEndPoints.DONATIONS_ENDPOINT + "/{id}")
    suspend fun delete(@Path("id") id: String): DonationWrapper

    @POST(ServiceEndPoints.DONATIONS_ENDPOINT)
    suspend fun post(@Body donation: DonationModel): DonationWrapper

    @PUT(ServiceEndPoints.DONATIONS_ENDPOINT + "/{id}")
    suspend fun put(@Path("id") id: String,
                    @Body donation: DonationModel
    ): DonationWrapper
}