package ie.setu.donationx.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.budgetDataStore by preferencesDataStore(name = "budget_data_store")

class BudgetDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.budgetDataStore

    companion object {
        private val BUDGET_KEY = intPreferencesKey("BUDGET_KEY")
    }

    val currentBudget: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[BUDGET_KEY] ?: 0
        }

    // Save the budget value
    suspend fun saveBudget(budget: Int) {
        dataStore.edit { preferences ->
            preferences[BUDGET_KEY] = budget
        }
    }
}
