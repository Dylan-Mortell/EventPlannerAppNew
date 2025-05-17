package ie.setu.donationx.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Unique ID for each event
    val eventName: String = "Unnamed Event", // Name of the event
    val eventDate: Date = Date(), // Date of the event
    val createdAt: Date = Date() // Timestamp when the event was created
)
