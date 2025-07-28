package com.example.chatter.feature.auth.signup

import androidx.lifecycle.ViewModel
import com.example.chatter.feature.auth.signin.SignInState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        _state.value = SignUpState.Loading

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    task.result.user?.let { user ->
                        val profileUpdates = userProfileChangeRequest {
                            displayName = name
                        }
                        user.updateProfile(profileUpdates).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _state.value = SignUpState.Success
                            } else {
                                _state.value = SignUpState.Error
                            }
                        }
                    } ?: run {
                        _state.value = SignUpState.Error
                    }
                } else {
                    _state.value = SignUpState.Error
                }
            }
    }
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    object Error : SignUpState()
}