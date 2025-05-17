package ie.setu.donationx.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.eventDataStore by preferencesDataStore(name = "event_data_store")

class EventDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.eventDataStore

    companion object {
        val EVENT_NAME_KEY = stringPreferencesKey("EVENT_NAME")
        val EVENT_DATE_KEY = stringPreferencesKey("EVENT_DATE")
    }

    val eventName: Flow<String> = dataStore.data.map { it[EVENT_NAME_KEY] ?: "" }
    val eventDate: Flow<String> = dataStore.data.map { it[EVENT_DATE_KEY] ?: "" }

    suspend fun saveEvent(name: String, date: String) {
        dataStore.edit { prefs ->
            prefs[EVENT_NAME_KEY] = name
            prefs[EVENT_DATE_KEY] = date
        }
    }
}
