package ie.setu.donationx.ui.components.report

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.donationx.R
import ie.setu.donationx.ui.theme.DonationXTheme
import ie.setu.donationx.ui.theme.endGradientColor
import ie.setu.donationx.ui.theme.startGradientColor
import java.text.DateFormat
import java.util.Date

@Composable
fun DonationCard(
    paymentType: String,
    paymentAmount: Int,
    message: String,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickDonationDetails: () -> Unit,
    onRefreshList: () -> Unit,
    onClickEdit: (String, Int) -> Unit
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        DonationCardContent(
            paymentType,
            paymentAmount,
            message,
            dateCreated,
            dateModified,
            onClickDelete,
            onClickDonationDetails,
            onRefreshList,
            onClickEdit
        )
    }
}

@Composable
private fun DonationCardContent(
    paymentType: String,
    paymentAmount: Int,
    message: String,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickDonationDetails: () -> Unit,
    onRefreshList: () -> Unit,
    onClickEdit: (String, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var currentMessage by remember { mutableStateOf(message) }
    var currentAmount by remember { mutableStateOf(paymentAmount) }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        startGradientColor,
                        endGradientColor,
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Business,
                    contentDescription = "Donation Status",
                    Modifier.padding(end = 8.dp)
                )
                Text(
                    text = paymentType,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "€$paymentAmount",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Text(
                text = "Donated $dateCreated", style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "Modified $dateModified", style = MaterialTheme.typography.labelSmall
            )
            if (expanded) {
                Text(modifier = Modifier.padding(vertical = 16.dp), text = message)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    FilledTonalButton(onClick = onClickDonationDetails) {
                        Text(text = "Show More")
                    }

                    // Edit Button
                    FilledTonalIconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Donation")
                    }

                    FilledTonalIconButton(onClick = {
                        showDeleteConfirmDialog = true
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Donation")
                    }

                    // Show Delete
                    if (showDeleteConfirmDialog) {
                        showDeleteAlert(
                            onDismiss = { showDeleteConfirmDialog = false },
                            onDelete = onClickDelete,
                            onRefresh = onRefreshList
                        )
                    }

                    // Show Edit Dialog
                    if (showEditDialog) {
                        EditDonationDialog(
                            currentMessage,
                            currentAmount,
                            onDismiss = { showEditDialog = false },
                            onEdit = { editedMessage, editedAmount ->
                                currentMessage = editedMessage
                                currentAmount = editedAmount

                                // Perform the edit operation in the parent composable/viewmodel
                                onClickEdit(editedMessage, editedAmount)

                                showEditDialog = false // Close the dialog
                            }
                        )
                    }
                }
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess
                else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun EditDonationDialog(
    currentMessage: String,
    currentAmount: Int,
    onDismiss: () -> Unit,
    onEdit: (String, Int) -> Unit // Callback with new data to save
) {
    var editedMessage by remember { mutableStateOf(currentMessage) }
    var editedAmount by remember { mutableStateOf(currentAmount.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Donation") },
        text = {
            Column {
                TextField(
                    value = editedMessage,
                    onValueChange = { editedMessage = it },
                    label = { Text("Message") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = editedAmount,
                    onValueChange = { editedAmount = it },
                    label = { Text("Amount (€)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Call the onEdit callback with the new data
                    if (editedAmount.isNotEmpty() && editedMessage.isNotEmpty()) {
                        onEdit(editedMessage, editedAmount.toInt())
                    }
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onRefresh: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Donation") },
        text = { Text("Are you sure you want to delete this donation?") },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    onRefresh()
                    onDismiss()
                }
            ) {
                Text("Yes, Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Preview
@Composable
fun DonationCardPreview() {
    DonationXTheme {
        DonationCard(
            paymentType = "Direct",
            paymentAmount = 100,
            message = """
                A message entered 
                by the user..."
            """.trimIndent(),
            dateCreated = DateFormat.getDateTimeInstance().format(Date()),
            dateModified = DateFormat.getDateTimeInstance().format(Date()),
            onClickDelete = { },
            onClickDonationDetails = {},
            onRefreshList = {},
            onClickEdit = { _, _ -> }
        )
    }
}
