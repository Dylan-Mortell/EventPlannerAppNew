package ie.setu.donationx.ui.screens.donate

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.donationx.R
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.fakeDonations
import ie.setu.donationx.ui.components.donate.AmountPicker
import ie.setu.donationx.ui.components.donate.DonateButton
import ie.setu.donationx.ui.components.donate.MessageInput
import ie.setu.donationx.ui.components.donate.ProgressBar
import ie.setu.donationx.ui.components.donate.RadioButtonGroup
import ie.setu.donationx.ui.components.donate.WelcomeText
import ie.setu.donationx.ui.screens.report.ReportViewModel
import ie.setu.donationx.ui.theme.DonationXTheme
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun DonateScreen(
    modifier: Modifier = Modifier,
    reportViewModel: ReportViewModel = hiltViewModel()
) {
    var paymentType by remember { mutableStateOf("Paypal") }
    var paymentAmount by remember { mutableIntStateOf(10) }
    var paymentMessage by remember { mutableStateOf("Donation!") }

    val donations by reportViewModel.uiDonations.collectAsState()
    var totalDonated = donations.sumOf { it.paymentAmount }

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.limitExceeded, paymentAmount)

    // Scrollable Column to avoid overflow
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .verticalScroll(scrollState), // Enables scrolling
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        WelcomeText()

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButtonGroup(
                modifier = modifier,
                onPaymentTypeChange = { paymentType = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            AmountPicker(
                onPaymentAmountChange = { paymentAmount = it }
            )
        }

        ProgressBar(modifier = modifier, totalDonated = totalDonated)

        MessageInput(
            modifier = modifier,
            onMessageChange = { paymentMessage = it }
        )


        Spacer(modifier = Modifier.weight(1f))

        DonateButton(
            modifier = modifier,
            donation = DonationModel(
                paymentType = paymentType,
                paymentAmount = paymentAmount,
                message = paymentMessage
            ),
            totalDonated = totalDonated,
            onDonateClick = {
                if (totalDonated + paymentAmount <= 10000) {
                    reportViewModel.createDonation(
                        DonationModel(
                            paymentType = paymentType,
                            paymentAmount = paymentAmount,
                            message = paymentMessage
                        )
                    )
                } else {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DonateScreenPreview() {
    DonationXTheme {
        PreviewDonateScreen( modifier = Modifier,
            donations = fakeDonations.toMutableStateList())
    }
}

@Composable
fun PreviewDonateScreen(modifier: Modifier = Modifier,
                        donations: SnapshotStateList<DonationModel>
) {
    var paymentType by remember { mutableStateOf("Paypal") }
    var paymentAmount by remember { mutableIntStateOf(10) }
    var paymentMessage by remember { mutableStateOf("Donation!") }
    var totalDonated by remember { mutableIntStateOf(0) }

    totalDonated = donations.sumOf { it.paymentAmount }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPaymentTypeChange = { paymentType = it }
                )
                Spacer(modifier.weight(1f))
                AmountPicker(
                    onPaymentAmountChange = { paymentAmount = it }
                )
            }
            ProgressBar(
                modifier = modifier,
                totalDonated = totalDonated)
            MessageInput(
                modifier = modifier,
                onMessageChange = { paymentMessage = it }
            )
            DonateButton(
                modifier = modifier,
                donation = DonationModel(paymentType = paymentType,
                    paymentAmount = paymentAmount,
                    message = paymentMessage),
                totalDonated = totalDonated,
                onDonateClick = {  }
            )
        }
    }
}
