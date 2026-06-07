package com.novafit.app.data.remote.api

import com.novafit.app.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface NovafitApi {

    // Auth
    @POST("auth/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // Miembros
    @GET("miembros/")
    suspend fun getMiembros(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("activo") activo: Boolean? = null
    ): Response<PaginatedResponse<MiembroDto>>

    @GET("miembros/{id}/")
    suspend fun getMiembro(@Path("id") id: Int): Response<MiembroDto>

    @POST("miembros/{id}/activar/")
    suspend fun activarMiembro(@Path("id") id: Int): Response<Any>

    @POST("miembros/{id}/desactivar/")
    suspend fun desactivarMiembro(@Path("id") id: Int): Response<Any>

    // Planes
    @GET("planes/")
    suspend fun getPlanes(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("activo") activo: Boolean? = null
    ): Response<PaginatedResponse<PlanDto>>

    @GET("planes/{id}/")
    suspend fun getPlan(@Path("id") id: Int): Response<PlanDto>

    @POST("planes/")
    suspend fun crearPlan(@Body plan: Map<String, @JvmSuppressWildcards Any>): Response<PlanDto>

    @PUT("planes/{id}/")
    suspend fun actualizarPlan(
        @Path("id") id: Int,
        @Body plan: Map<String, @JvmSuppressWildcards Any>
    ): Response<PlanDto>

    @DELETE("planes/{id}/")
    suspend fun eliminarPlan(@Path("id") id: Int): Response<Unit>

    // Membresías
    @GET("membresias/")
    suspend fun getMembresias(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("estado") estado: String? = null
    ): Response<PaginatedResponse<MembresiaDto>>

    @GET("membresias/{id}/")
    suspend fun getMembresia(@Path("id") id: Int): Response<MembresiaDto>

    @POST("membresias/")
    suspend fun crearMembresia(@Body membresia: Map<String, @JvmSuppressWildcards Any>): Response<MembresiaDto>

    @PUT("membresias/{id}/")
    suspend fun actualizarMembresia(
        @Path("id") id: Int,
        @Body membresia: Map<String, @JvmSuppressWildcards Any>
    ): Response<MembresiaDto>

    @DELETE("membresias/{id}/")
    suspend fun eliminarMembresia(@Path("id") id: Int): Response<Unit>

    @POST("membresias/{id}/cancelar/")
    suspend fun cancelarMembresia(@Path("id") id: Int): Response<Any>

    // Pagos
    @GET("pagos/")
    suspend fun getPagos(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("estado") estado: String? = null
    ): Response<PaginatedResponse<PagoDto>>

    @GET("pagos/{id}/")
    suspend fun getPago(@Path("id") id: Int): Response<PagoDto>

    @POST("pagos/")
    suspend fun crearPago(@Body pago: Map<String, @JvmSuppressWildcards Any>): Response<PagoDto>

    @POST("pagos/{id}/completar/")
    suspend fun completarPago(@Path("id") id: Int): Response<Any>

    @DELETE("pagos/{id}/")
    suspend fun eliminarPago(@Path("id") id: Int): Response<Unit>

    // Entrenadores
    @GET("entrenadores/")
    suspend fun getEntrenadores(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10
    ): Response<PaginatedResponse<EntrenadorDto>>

    @GET("entrenadores/{id}/")
    suspend fun getEntrenador(@Path("id") id: Int): Response<EntrenadorDto>

    @POST("entrenadores/{id}/activar/")
    suspend fun activarEntrenador(@Path("id") id: Int): Response<Any>

    @POST("entrenadores/{id}/desactivar/")
    suspend fun desactivarEntrenador(@Path("id") id: Int): Response<Any>

    // Clases
    @GET("clases/")
    suspend fun getClases(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("dia") dia: String? = null
    ): Response<PaginatedResponse<ClaseDto>>

    @GET("clases/{id}/")
    suspend fun getClase(@Path("id") id: Int): Response<ClaseDto>

    @POST("clases/")
    suspend fun crearClase(@Body clase: Map<String, @JvmSuppressWildcards Any>): Response<ClaseDto>

    @PUT("clases/{id}/")
    suspend fun actualizarClase(
        @Path("id") id: Int,
        @Body clase: Map<String, @JvmSuppressWildcards Any>
    ): Response<ClaseDto>

    @DELETE("clases/{id}/")
    suspend fun eliminarClase(@Path("id") id: Int): Response<Unit>

    // Asistencias
    @GET("asistencias/")
    suspend fun getAsistencias(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10
    ): Response<PaginatedResponse<AsistenciaDto>>

    @POST("asistencias/registrar-entrada/")
    suspend fun registrarEntrada(@Body body: Map<String, @JvmSuppressWildcards Any>): Response<AsistenciaDto>

    @POST("asistencias/{id}/registrar-salida/")
    suspend fun registrarSalida(@Path("id") id: Int): Response<AsistenciaDto>

    @GET("asistencias/hoy/")
    suspend fun getAsistenciasHoy(): Response<Any>
}
