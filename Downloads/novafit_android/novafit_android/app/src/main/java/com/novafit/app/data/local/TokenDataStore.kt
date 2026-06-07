package com.novafit.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "novafit_prefs")

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ROL = stringPreferencesKey("user_rol")
        val USERNAME = stringPreferencesKey("username")
    }

    val accessToken: Flow<String?> = context.dataStore.data.map { it[ACCESS_TOKEN] }
    val refreshToken: Flow<String?> = context.dataStore.data.map { it[REFRESH_TOKEN] }
    val userRol: Flow<String?> = context.dataStore.data.map { it[USER_ROL] }
    val username: Flow<String?> = context.dataStore.data.map { it[USERNAME] }

    suspend fun guardarTokens(access: String, refresh: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = access
            prefs[REFRESH_TOKEN] = refresh
        }
    }

    suspend fun guardarUsuario(rol: String, username: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ROL] = rol
            prefs[USERNAME] = username
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { it.clear() }
    }
}
