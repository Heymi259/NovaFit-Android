package com.novafit.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novafit.app.data.local.TokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    val username = tokenDataStore.username

    fun cerrarSesion() {
        viewModelScope.launch {
            tokenDataStore.cerrarSesion()
        }
    }
}
