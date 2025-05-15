package ie.setu.donationx.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date


@Entity
data class DonationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val _id: String = "N/A",
    @SerializedName("paymenttype")
    val paymentType: String = "N/A",
    @SerializedName("paymentamount")
    val paymentAmount: Int = 0,
    var message: String = "Here is a donation!",
    @SerializedName("datedonated")
    val dateDonated: Date = Date(),
    @SerializedName("datemodified")
    val dateModified: Date = Date()
)

val fakeDonations = List(30) { i ->
    DonationModel(id = 12345 + i,
        _id = "12345" + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date()
    )
}
