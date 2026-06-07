package com.novafit.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Auth
@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val access: String,
    val refresh: String
)

// Paginación
@Serializable
data class PaginatedResponse<T>(
    val total: Int,
    val paginas: Int,
    @SerialName("pagina_actual") val paginaActual: Int,
    val siguiente: String? = null,
    val anterior: String? = null,
    val resultados: List<T>
)

// Usuario
@Serializable
data class UsuarioDto(
    val id: Int,
    val username: String,
    val email: String,
    @SerialName("first_name") val firstName: String = "",
    @SerialName("last_name") val lastName: String = "",
    val telefono: String = "",
    val rol: String = "miembro"
)

// Miembro
@Serializable
data class MiembroDto(
    val id: Int,
    val usuario: UsuarioDto,
    @SerialName("nombre_completo") val nombreCompleto: String = "",
    @SerialName("fecha_nacimiento") val fechaNacimiento: String? = null,
    val genero: String = "",
    val direccion: String = "",
    val cedula: String = "",
    val activo: Boolean = true,
    @SerialName("fecha_ingreso") val fechaIngreso: String = ""
)

// Plan
@Serializable
data class PlanDto(
    val id: Int,
    val nombre: String,
    val descripcion: String = "",
    val precio: String,
    @SerialName("duracion_dias") val duracionDias: Int,
    val frecuencia: String,
    @SerialName("max_clases_semana") val maxClasesSemana: Int? = null,
    val activo: Boolean = true
)

// Membresía
@Serializable
data class MembresiaDto(
    val id: Int,
    val miembro: Int,
    @SerialName("nombre_miembro") val nombreMiembro: String = "",
    val plan: PlanDto? = null,
    @SerialName("plan_id") val planId: Int? = null,
    @SerialName("fecha_inicio") val fechaInicio: String,
    @SerialName("fecha_fin") val fechaFin: String,
    val estado: String,
    val notas: String = ""
)

// Pago
@Serializable
data class PagoDto(
    val id: Int,
    val membresia: Int,
    @SerialName("nombre_miembro") val nombreMiembro: String = "",
    @SerialName("nombre_plan") val nombrePlan: String = "",
    val monto: String,
    @SerialName("metodo_pago") val metodoPago: String,
    val estado: String,
    val referencia: String = "",
    val notas: String = "",
    @SerialName("fecha_pago") val fechaPago: String = ""
)

// Entrenador
@Serializable
data class EntrenadorDto(
    val id: Int,
    val usuario: UsuarioDto,
    @SerialName("nombre_completo") val nombreCompleto: String = "",
    val especialidad: String,
    val certificaciones: String = "",
    val biografia: String = "",
    val activo: Boolean = true,
    @SerialName("fecha_ingreso") val fechaIngreso: String = ""
)

// Clase
@Serializable
data class ClaseDto(
    val id: Int,
    val nombre: String,
    val descripcion: String = "",
    val entrenador: EntrenadorResumenDto? = null,
    @SerialName("entrenador_id") val entrenadorId: Int? = null,
    val dia: String,
    @SerialName("hora_inicio") val horaInicio: String,
    @SerialName("hora_fin") val horaFin: String,
    @SerialName("capacidad_maxima") val capacidadMaxima: Int,
    @SerialName("total_inscritos") val totalInscritos: Int = 0,
    val activa: Boolean = true
)

@Serializable
data class EntrenadorResumenDto(
    val id: Int,
    @SerialName("nombre_completo") val nombreCompleto: String = "",
    val especialidad: String = ""
)

// Asistencia
@Serializable
data class AsistenciaDto(
    val id: Int,
    val miembro: Int,
    @SerialName("nombre_miembro") val nombreMiembro: String = "",
    val clase: Int? = null,
    @SerialName("nombre_clase") val nombreClase: String = "",
    @SerialName("fecha_hora_entrada") val fechaHoraEntrada: String = "",
    @SerialName("fecha_hora_salida") val fechaHoraSalida: String? = null,
    val notas: String = ""
)
