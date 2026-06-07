package com.novafit.app.ui.screens.miembros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novafit.app.data.remote.api.NovafitApi
import com.novafit.app.data.remote.dto.MiembroDto
import com.novafit.app.ui.common.UiState
import com.novafit.app.ui.common.parseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiembrosViewModel @Inject constructor(
    private val api: NovafitApi
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<List<MiembroDto>>>(UiState.Vacio)
    val estado = _estado.asStateFlow()

    private val _hayMas = MutableStateFlow(false)
    val hayMas = _hayMas.asStateFlow()

    private var paginaActual = 1
    private val listaMiembros = mutableListOf<MiembroDto>()

    fun cargarMiembros() {
        paginaActual = 1
        listaMiembros.clear()
        cargar()
    }

    fun cargarMas() {
        paginaActual++
        cargar()
    }

    private fun cargar() {
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            try {
                val respuesta = api.getMiembros(page = paginaActual)
                if (respuesta.isSuccessful) {
                    val body = respuesta.body()!!
                    listaMiembros.addAll(body.resultados)
                    _hayMas.value = body.siguiente != null
                    _estado.value = if (listaMiembros.isEmpty()) UiState.Vacio
                    else UiState.Exito(listaMiembros.toList())
                } else {
                    _estado.value = UiState.Error(parseError(respuesta.code()))
                }
            } catch (e: Exception) {
                _estado.value = UiState.Error("Sin conexión a internet")
            }
        }
    }

    fun activarMiembro(id: Int) {
        viewModelScope.launch {
            try {
                api.activarMiembro(id)
                cargarMiembros()
            } catch (e: Exception) { }
        }
    }

    fun desactivarMiembro(id: Int) {
        viewModelScope.launch {
            try {
                api.desactivarMiembro(id)
                cargarMiembros()
            } catch (e: Exception) { }
        }
    }
}
