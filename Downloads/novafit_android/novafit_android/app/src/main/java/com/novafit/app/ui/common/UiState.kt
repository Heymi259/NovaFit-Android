package com.novafit.app.ui.common

sealed class UiState<out T> {
    object Cargando : UiState<Nothing>()
    data class Exito<T>(val data: T) : UiState<T>()
    data class Error(val mensaje: String) : UiState<Nothing>()
    object Vacio : UiState<Nothing>()
}

fun <T> Result<T>.toUiState(): UiState<T> {
    return if (isSuccess) {
        val data = getOrNull()
        if (data != null) UiState.Exito(data)
        else UiState.Vacio
    } else {
        UiState.Error(exceptionOrNull()?.message ?: "Error desconocido")
    }
}

fun parseError(code: Int): String {
    return when (code) {
        400 -> "Datos inválidos. Revisa la información ingresada."
        401 -> "No autorizado. Por favor inicia sesión nuevamente."
        403 -> "No tienes permisos para realizar esta acción."
        404 -> "Registro no encontrado."
        500 -> "Error interno del servidor. Intenta más tarde."
        else -> "Error inesperado (código: $code)"
    }
}
