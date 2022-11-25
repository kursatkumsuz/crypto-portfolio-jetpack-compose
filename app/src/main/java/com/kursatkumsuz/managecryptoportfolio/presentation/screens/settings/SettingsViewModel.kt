package com.kursatkumsuz.managecryptoportfolio.presentation.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.model.user.UserModel
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth.GetUserInfoUseCase
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth.SignOutUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private var _userInfoState = mutableStateOf(UserModel("", ""))
    val userInfoState = _userInfoState

    init {
        loadUserInfo()
    }
    fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.invoke().collect {
                var userModel = UserModel("","")
                when(it) {
                    is Response.Success -> {
                        it.data?.let {  snapshot ->
                            for(i in snapshot ) {
                                userModel = UserModel(i.get("name").toString(),i.get("email").toString())
                            }
                         }
                    _userInfoState.value = userModel
                    }
                    is Response.Loading -> {}
                    is Response.Error -> {}
                }
            }
        }
    }
}