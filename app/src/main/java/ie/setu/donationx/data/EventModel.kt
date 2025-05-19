package ie.setu.donationx.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long  = 0,
    val eventName: String = "Unnamed Event",
    val eventDate: Date = Date(),
    val createdAt: Date = Date(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0

)
