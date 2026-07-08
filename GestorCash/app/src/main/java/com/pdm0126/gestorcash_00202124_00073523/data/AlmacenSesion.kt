package com.pdm0126.gestorcash_00202124_00073523.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "sesion")

// guarda y lee la sesion del usuario con datastore
object AlmacenSesion {

    private val LLAVE_TOKEN = stringPreferencesKey("token")
    private val LLAVE_NOMBRE = stringPreferencesKey("nombre")

    suspend fun guardar(context: Context, token: String, nombre: String) {
        context.dataStore.edit { prefs ->
            prefs[LLAVE_TOKEN] = token
            prefs[LLAVE_NOMBRE] = nombre
        }
    }

    suspend fun obtenerToken(context: Context): String? {
        return context.dataStore.data.first()[LLAVE_TOKEN]
    }

    suspend fun obtenerNombre(context: Context): String? {
        return context.dataStore.data.first()[LLAVE_NOMBRE]
    }

    suspend fun cerrar(context: Context) {
        context.dataStore.edit { prefs -> prefs.clear() }
    }
}