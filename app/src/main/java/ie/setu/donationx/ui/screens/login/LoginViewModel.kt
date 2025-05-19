package ie.setu.donationx.ui.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.donationx.firebase.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginState: StateFlow<LoginResult> = _loginState

    fun login(email: String, password: String) {
        _loginState.value = LoginResult.Loading
        viewModelScope.launch {
            val result = authService.login(email, password)
            _loginState.value = if (result.isSuccess) {
                LoginResult.Success
            } else {
                LoginResult.Error(result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginResult.Idle
    }
}

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}