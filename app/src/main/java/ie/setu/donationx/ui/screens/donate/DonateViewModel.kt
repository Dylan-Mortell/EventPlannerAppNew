package ie.setu.donationx.ui.screens.donate

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.data.DonationModel
import ie.setu.donationx.data.api.RetrofitRepository
import ie.setu.donationx.data.room.RoomRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject
constructor(private val repository: RetrofitRepository)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

//    fun insert(donation: DonationModel)
//            = viewModelScope.launch {
//                repository.insert(donation)
//    }

    fun insert(donation: DonationModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(donation)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
}
