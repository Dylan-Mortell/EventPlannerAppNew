package ie.setu.donationx.ui.screens.eventplanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.data.EventDataStorage
import ie.setu.donationx.data.EventModel
import ie.setu.donationx.data.firebase.FirebaseEventRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

@HiltViewModel
class EventPlannerViewModel @Inject constructor(
    private val eventDataStorage: EventDataStorage,
    private val eventRepository: FirebaseEventRepository
) : ViewModel() {

    private val _savedEvents = MutableStateFlow<List<EventModel>>(emptyList())
    val savedEvents: StateFlow<List<EventModel>> = _savedEvents

    // Other state variables for event name, date, etc.
    private val _eventName = MutableStateFlow("")
    val eventName: StateFlow<String> = _eventName

    private val _eventDate = MutableStateFlow("")
    val eventDate: StateFlow<String> = _eventDate

    private val _eventMessage = MutableStateFlow<String?>(null)
    val eventMessage: StateFlow<String?> = _eventMessage

    private val _currentEvent = MutableStateFlow<EventModel?>(null)
    val currentEvent: StateFlow<EventModel?> = _currentEvent

    private val _eventLocation = MutableStateFlow<LatLng?>(null)
    val eventLocation: StateFlow<LatLng?> = _eventLocation

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            eventRepository.getAllEvents().collect { events ->
                _savedEvents.value = events
            }
        }
    }

    // Function to save or update an event
    fun saveEvent() {
        viewModelScope.launch {
            if (_eventName.value.isBlank() || _eventDate.value.isBlank()) {
                _eventMessage.value = "Event name and date must not be empty."
                return@launch
            }

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = try {
                formatter.parse(_eventDate.value)
            } catch (e: Exception) {
                null
            }

            if (parsedDate == null) {
                _eventMessage.value = "Invalid date format. Use yyyy-MM-dd."
                return@launch
            }

            val location = _eventLocation.value
            if (location == null) {
                _eventMessage.value = "Event location must be selected."
                return@launch
            }

            try {
                val event = EventModel(
                    id = _currentEvent.value?.id ?: 0L,
                    eventName = _eventName.value,
                    eventDate = parsedDate,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    createdAt = Date()
                )
                eventRepository.saveEvent(event)
                _eventMessage.value = if (_currentEvent.value == null) "Event saved!" else "Event updated!"

                _currentEvent.value = null
                _eventName.value = ""
                _eventDate.value = ""
                _eventLocation.value = null

                eventDataStorage.saveEvent(_eventName.value, _eventDate.value)

                loadEvents()

            } catch (e: Exception) {
                Log.e("EventPlannerViewModel", "Error saving event", e)
                _eventMessage.value = "Failed to save event: ${e.localizedMessage}"
            }
        }
    }

    // Function to delete an event
    fun deleteEvent(event: EventModel) {
        viewModelScope.launch {
            try {
                eventRepository.deleteEvent(event)

                _eventMessage.value = "Event deleted."
                loadEvents()

            } catch (e: Exception) {
                Log.e("EventPlannerViewModel", "Error deleting event", e)
                _eventMessage.value = "Failed to delete event: ${e.localizedMessage}"
            }
        }
    }

    fun resetEditState() {
        _currentEvent.value = null
        _eventName.value = ""
        _eventDate.value = ""
        _eventLocation.value = null
    }

    fun setEventToEdit(event: EventModel) {
        _currentEvent.value = event
        _eventName.value = event.eventName
        _eventDate.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(event.eventDate)
        _eventLocation.value = LatLng(event.latitude, event.longitude)
    }

    fun updateEventName(name: String) { _eventName.value = name }
    fun updateEventDate(date: String) { _eventDate.value = date }
    fun updateEventLocation(location: LatLng) { _eventLocation.value = location }

    fun clearMessage() { _eventMessage.value = null }
}
