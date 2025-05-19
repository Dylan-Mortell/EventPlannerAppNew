package ie.setu.donationx.firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginResult = MutableStateFlow<Result<Boolean>?>(null)
    val loginResult: StateFlow<Result<Boolean>?> = _loginResult

    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    _loginResult.value = if (task.isSuccessful) {
                        Result.success(true)
                    } else {
                        Result.failure(task.exception ?: Exception("Unknown error"))
                    }
                }
            }
    }

    fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    _loginResult.value = if (task.isSuccessful) {
                        Result.success(true)
                    } else {
                        Result.failure(task.exception ?: Exception("Unknown error"))
                    }
                }
            }
    }

    fun resetLoginResult() {
        _loginResult.value = null
    }
}
