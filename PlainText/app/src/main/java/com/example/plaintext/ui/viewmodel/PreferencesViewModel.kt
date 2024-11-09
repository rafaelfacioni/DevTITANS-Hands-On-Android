package com.example.plaintext.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.plaintext.data.di.loginInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PreferencesState(
    var login: String,
    var password: String,
    var preencher: Boolean
)

data class LoginState(
    var login: String,
    var password: String,
    var saveCredentials: Boolean
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val loginInfo: loginInfo,
    handle: SavedStateHandle,
) : ViewModel() {
    var preferencesState by mutableStateOf(PreferencesState(login = loginInfo.login, password = loginInfo.password, preencher = false))
        private set
    var loginState = mutableStateOf(LoginState(loginInfo.login, loginInfo.password, false))

    fun updateLogin(login: String) {
        loginInfo.login = login
        preferencesState = preferencesState.copy(login = login)
    }

    fun updatePassword(password: String) {
        loginInfo.password = password
        preferencesState = preferencesState.copy(password = password)
    }

    fun updatePreencher(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher = preencher)
    }

    fun checkCredentials(login: String, password: String): Boolean {
        return login == loginInfo.login && password == loginInfo.password
    }

    fun updateSaveCredentials(save: Boolean) {
        loginState.value = loginState.value.copy(saveCredentials = save)

        if (save) {
            loginInfo.login = loginState.value.login
            loginInfo.password = loginState.value.password
        }
    }
}
