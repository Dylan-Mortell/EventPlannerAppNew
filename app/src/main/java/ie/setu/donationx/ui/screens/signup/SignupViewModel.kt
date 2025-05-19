package ie.setu.donationx.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.firebase.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _signupResult = MutableStateFlow<Result<Unit>?>(null)
    val signupResult: StateFlow<Result<Unit>?> = _signupResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = authService.register(email, password)
            _signupResult.value = result
        }
    }

    fun clearResult() {
        _signupResult.value = null
    }
}
