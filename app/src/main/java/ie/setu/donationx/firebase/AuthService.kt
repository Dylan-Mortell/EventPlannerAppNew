package ie.setu.donationx.firebase

interface AuthService {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    fun logout()
    fun getCurrentUserEmail(): String?
}
