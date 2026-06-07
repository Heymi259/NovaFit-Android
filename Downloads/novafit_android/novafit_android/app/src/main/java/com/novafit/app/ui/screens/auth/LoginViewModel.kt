package com.novafit.app.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novafit.app.data.local.TokenDataStore
import com.novafit.app.data.remote.api.NovafitApi
import com.novafit.app.data.remote.dto.LoginRequest
import com.novafit.app.ui.common.UiState
import com.novafit.app.ui.common.parseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: NovafitApi,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<Unit>>(UiState.Vacio)
    val estado = _estado.asStateFlow()

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _estado.value = UiState.Error("Por favor ingresa usuario y contraseña")
            return
        }
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            try {
                val respuesta = api.login(LoginRequest(username, password))
                if (respuesta.isSuccessful) {
                    val body = respuesta.body()!!
                    tokenDataStore.guardarTokens(body.access, body.refresh)
                    tokenDataStore.guardarUsuario("admin", username)
                    _estado.value = UiState.Exito(Unit)
                } else {
                    _estado.value = UiState.Error(parseError(respuesta.code()))
                }
            } catch (e: Exception) {
                _estado.value = UiState.Error("Sin conexión a internet. Verifica tu red.")
            }
        }
    }
}
