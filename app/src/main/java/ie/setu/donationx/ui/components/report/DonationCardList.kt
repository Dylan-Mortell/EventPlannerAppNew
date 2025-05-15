package ie.setu.donationx.ui.components.report

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.fakeDonations
import ie.setu.donationx.ui.theme.DonationXTheme
import java.text.DateFormat

@Composable
internal fun DonationCardList(
    donations: List<DonationModel>,
    modifier: Modifier = Modifier,
    onDeleteDonation: (DonationModel) -> Unit,
    onClickDonationDetails: (String) -> Unit,
    onEditDonation: (String, Int) -> Unit,
    onRefreshList: () -> Unit,
) {
    LazyColumn {
        items(
            items = donations,
            key = { donation -> donation._id }
        ) { donation ->
            DonationCard(
                paymentType = donation.paymentType,
                paymentAmount = donation.paymentAmount,
                message = donation.message,
                dateCreated = DateFormat.getDateTimeInstance().format(donation.dateDonated),
                dateModified = DateFormat.getDateTimeInstance().format(donation.dateModified),
                onClickDelete = { onDeleteDonation(donation) },
                onClickDonationDetails = { onClickDonationDetails(donation._id) },
                onClickEdit = { message, amount -> onEditDonation(message, amount) },
                onRefreshList = onRefreshList
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonationCardListPreview() {
    DonationXTheme {
        DonationCardList(
            donations = fakeDonations.toMutableStateList(),
            onDeleteDonation = {},
            onClickDonationDetails = { },
            onEditDonation = { message, amount ->
                println("Editing donation: Message: $message, Amount: $amount")
            },
            onRefreshList = { }
        )
    }
}
