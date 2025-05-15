package ie.setu.donationx.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.api.RetrofitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {

    private val _donations = MutableStateFlow<List<DonationModel>>(emptyList())
    val uiDonations: StateFlow<List<DonationModel>> = _donations.asStateFlow()

    var isErr = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init {
        getDonations()
    }

    // Fetch all donations
    fun getDonations() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                _donations.value = repository.getAll() // Fetch donations from the repository
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                isLoading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    // Create a new donation
    fun createDonation(donation: DonationModel) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val donationWrapper = repository.insert(donation)

                if (donationWrapper.data != null) {
                    getDonations() // Refresh donations list after creating a new one
                } else {
                    Timber.e("Donation creation failed: ${donationWrapper.message}")
                }

                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                isLoading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    // Update an existing donation
    fun updateDonation(donation: DonationModel) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val donationWrapper = repository.update(donation)

                if (donationWrapper.data != null) {
                    getDonations() // Refresh donations list after updating the donation
                } else {
                    Timber.e("Donation update failed: ${donationWrapper.message}")
                }

                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                isLoading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    // Delete a donation
    fun deleteDonation(donation: DonationModel) {
        viewModelScope.launch {
            repository.delete(donation)
            getDonations() // Refresh donations list after deletion
        }
    }
}

