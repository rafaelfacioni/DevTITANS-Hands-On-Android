package com.example.plaintext.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.LocalPasswordDBStore
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListViewState(
    var passwordList: List<PasswordInfo>,
    var isCollected: Boolean = false
)

//Utilize o passwordBDStore para obter a lista de senhas e salva-las
@HiltViewModel
open class ListViewModel @Inject constructor(private val passwordDBStore: PasswordDBStore) : ViewModel() {
    var listViewState by mutableStateOf(ListViewState(passwordList = emptyList(), isCollected = false))
        private set

    init {
        viewModelScope.launch {
            passwordDBStore.getList().collect { passwords ->
                listViewState = listViewState.copy(
                    passwordList = passwords.map {
                        PasswordInfo(
                            id = it.id,
                            name = it.name,
                            login = it.login,
                            password = it.password,
                            notes = it.notes.toString()
                        )
                    },
                    isCollected = true
                )
            }
        }
    }

    fun savePassword(password: PasswordInfo){
        viewModelScope.launch {
            passwordDBStore.save(password)
            Log.d("Senha Salva", "savePassword: ${password.name}, ${password.login}")
        }
    }

}
