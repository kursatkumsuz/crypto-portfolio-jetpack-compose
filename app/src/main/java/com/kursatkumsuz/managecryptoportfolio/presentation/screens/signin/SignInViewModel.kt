package com.kursatkumsuz.managecryptoportfolio.presentation.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth.SignInUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _signInFlow = MutableSharedFlow<Response<AuthResult>>()
    val signInFlow = _signInFlow

    fun signIn(email: String, password: String) = viewModelScope.launch {
        signInUseCase.invoke(email, password).collect { response ->
            _signInFlow.emit(response)
        }
    }
}