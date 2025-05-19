package ie.setu.donationx.ui.screens.budget

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.donationx.data.fakeDonations
import ie.setu.donationx.ui.components.donate.AmountPicker
import ie.setu.donationx.ui.components.donate.ProgressBar
import ie.setu.donationx.ui.theme.DonationXTheme

@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    budgetScreenViewModel: BudgetScreenViewModel = hiltViewModel()
) {

    val currentBudget by budgetScreenViewModel.currentBudget.collectAsState(initial = 1000)
    val isLoading by budgetScreenViewModel.isLoading.collectAsState(initial = false)
    val isError by budgetScreenViewModel.isError.collectAsState(initial = false)
    val errorMessage by budgetScreenViewModel.errorMessage.collectAsState(initial = "")

    // Calculate total donated and remaining budget
    val donations = fakeDonations
    val totalDonated = donations.sumOf { it.paymentAmount }
    val remainingBudget = currentBudget - totalDonated

    val context = LocalContext.current

    if (remainingBudget < 0) {
        Toast.makeText(context, "You have exceeded your budget!", Toast.LENGTH_SHORT).show()
    }

    val scrollState = rememberScrollState()

    // Update the UI
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(text = "Set Your Budget")

        // Amount picker to set budget
        AmountPicker(
            initialAmount = currentBudget,
            onPaymentAmountChange = { budgetScreenViewModel.saveBudget(it) }
        )

        // Progress bar showing donation progress
        ProgressBar(
            modifier = modifier,
            totalDonated = totalDonated,
            maxAmount = currentBudget
        )

        Text(
            text = "Total Donated: €$totalDonated",
            modifier = Modifier.align(Alignment.Start)
        )

        Text(
            text = "Remaining Budget: €$remainingBudget",
            modifier = Modifier.align(Alignment.Start)
        )

        // Show loading message
        if (isLoading) {
            Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Show error message if there was an error
        if (isError) {
            Text(
                text = "Error: $errorMessage",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Save Button
        Button(
            onClick = {
                if (remainingBudget >= 0) {
                    budgetScreenViewModel.saveBudget(currentBudget)
                    Toast.makeText(context, "Budget saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Cannot set a negative budget!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Budget")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview() {
    DonationXTheme {
        BudgetScreen()
    }
}

@Composable
fun BudgetScreenPreviewContent() {
    var budgetAmount by remember { mutableIntStateOf(1000) }
    val totalDonated = fakeDonations.sumOf { it.paymentAmount }

    val remainingBudget = budgetAmount - totalDonated

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Budget Preview")
        AmountPicker(
            initialAmount = budgetAmount,
            onPaymentAmountChange = { budgetAmount = it }
        )
        ProgressBar(
            totalDonated = totalDonated,
            maxAmount = budgetAmount
        )
        Text("Total Donated: €$totalDonated")
        Text("Remaining Budget: €$remainingBudget")
    }
}
