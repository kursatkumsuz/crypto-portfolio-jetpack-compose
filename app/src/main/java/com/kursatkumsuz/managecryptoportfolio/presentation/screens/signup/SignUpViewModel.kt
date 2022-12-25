package com.kursatkumsuz.managecryptoportfolio.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.kursatkumsuz.managecryptoportfolio.domain.model.user.UserModel
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth.SignUpUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    private var _signUpFlow = MutableSharedFlow<Response<AuthResult>>()
    val signUpFlow = _signUpFlow

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            signUpUseCase.invoke(name, email, password).collect {
                _signUpFlow.emit(it)
            }
        }
    }

}