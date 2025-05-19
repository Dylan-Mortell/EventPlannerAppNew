package ie.setu.donationx.ui.screens.eventplanner

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ie.setu.donationx.ui.theme.DonationXTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventPlannerScreen(
    modifier: Modifier = Modifier,
    viewModel: EventPlannerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val eventName by viewModel.eventName.collectAsState()
    val eventDate by viewModel.eventDate.collectAsState()
    val eventLocation by viewModel.eventLocation.collectAsState()
    val savedEvents by viewModel.savedEvents.collectAsState(initial = emptyList())
    val currentEvent by viewModel.currentEvent.collectAsState()

    // Track the focus state of the event name and date fields
    val eventNameFocusRequester = remember { FocusRequester() }
    val eventDateFocusRequester = remember { FocusRequester() }

    // Track the camera position and selected location
    val irelandCenter = LatLng(53.41291, -8.24389)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(irelandCenter, 6f)
    }

    // Handle map click
    val onMapClick: (LatLng) -> Unit = { latLng ->
        viewModel.updateEventLocation(latLng) // Update location in ViewModel
        cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 14f)
    }

    LaunchedEffect(currentEvent) {
        if (currentEvent != null) {
            eventNameFocusRequester.requestFocus()
        }
    }

    // Wrap the Column in a Scrollable modifier
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Makes the Column scrollable
    ) {
        Text("Event Planner", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        // Event Name Field
        TextField(
            value = eventName,
            onValueChange = { viewModel.updateEventName(it) },
            label = { Text("Enter Event Name") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(eventNameFocusRequester)
                .onFocusChanged { if (it.isFocused) { viewModel.clearMessage() } }
        )

        Spacer(Modifier.height(16.dp))

        // Event Date Field
        TextField(
            value = eventDate,
            onValueChange = { viewModel.updateEventDate(it) },
            label = { Text("Enter Event Date (yyyy-MM-dd)") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(eventDateFocusRequester)
                .onFocusChanged { if (it.isFocused) { viewModel.clearMessage() } }
        )

        Spacer(Modifier.height(16.dp))

        // Google Map for selecting event location
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = onMapClick
        ) {
            eventLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Event Location",
                    snippet = "Event will be held here"
                )
            }
        }

        eventLocation?.let {
            Text("Selected Location: Lat: ${it.latitude}, Lng: ${it.longitude}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.height(24.dp))

        // Save or Update Event Button
        Button(
            onClick = {
                viewModel.saveEvent()
                Toast.makeText(
                    context,
                    if (currentEvent == null) "Event saved!" else "Event updated!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (currentEvent == null) "Create Event" else "Update Event")
        }

        Spacer(Modifier.height(32.dp))

        Text("Saved Events:", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        savedEvents.forEach { event ->
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateStr = formatter.format(event.eventDate)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("• ${event.eventName} on $dateStr", modifier = Modifier.weight(1f))

                // Edit Button
                IconButton(
                    onClick = {
                        viewModel.setEventToEdit(event)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Event")
                }

                // Delete Button
                IconButton(
                    onClick = {
                        viewModel.deleteEvent(event)
                        Toast.makeText(context, "Event deleted!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Event")
                }
            }

            // Display saved event's location
            Text("Location: Lat: ${event.latitude}, Lng: ${event.longitude}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EventPlannerScreenPreview() {
    DonationXTheme {
        EventPlannerScreen()
    }
}
