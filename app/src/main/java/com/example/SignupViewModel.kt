package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState

    fun signUpUser(email: String, password: String, confirmPass: String, name: String) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            _signupState.value = SignupState.Error("Please fill in all fields")
            return
        }

        if (password != confirmPass) {
            _signupState.value = SignupState.Error("Passwords do not match")
            return
        }

        if (password.length < 6) {
            _signupState.value = SignupState.Error("Password must be at least 6 characters")
            return
        }

        _signupState.value = SignupState.Loading

        viewModelScope.launch {
            kotlinx.coroutines.delay(1000) // Simulate network delay
            _signupState.value = SignupState.Success
        }
    }
}

sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    object Success : SignupState()
    data class Error(val message: String) : SignupState()
}
