package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SigninViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _signinState = MutableStateFlow<SigninState>(SigninState.Idle)
    val signinState: StateFlow<SigninState> = _signinState

    fun signInUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _signinState.value = SigninState.Error("Please fill in all fields")
            return
        }

        _signinState.value = SigninState.Loading

        viewModelScope.launch {
            kotlinx.coroutines.delay(1000) // Simulate network delay
            _signinState.value = SigninState.Success
        }
    }
}

sealed class SigninState {
    object Idle : SigninState()
    object Loading : SigninState()
    object Success : SigninState()
    data class Error(val message: String) : SigninState()
}
