package ie.setu.donationx.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.data.BudgetDataStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BudgetScreenViewModel @Inject constructor(
    private val budgetDataStore: BudgetDataStorage
) : ViewModel() {

    private val _currentBudget = MutableStateFlow(1000)  // Default budget value
    val currentBudget: StateFlow<Int> = _currentBudget

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error state
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Load the budget from the DataStore
    init {
        loadBudget()
    }

    // Function to load the budget
    private fun loadBudget() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                budgetDataStore.currentBudget.collect { budget ->
                    _currentBudget.value = budget
                }
            } catch (e: Exception) {
                _isError.value = true
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to save the budget to the DataStore
    fun saveBudget(budget: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                budgetDataStore.saveBudget(budget)
                _currentBudget.value = budget
            } catch (e: Exception) {
                _isError.value = true
                _errorMessage.value = e.message ?: "An error occurred while saving the budget"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
