package com.pdm0126.gestorcash_00202124_00073523.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Rutas : NavKey {
    @Serializable data object Login : Rutas()
    @Serializable data object Registro : Rutas()
    @Serializable data object Inicio : Rutas()
    @Serializable data object Agregar : Rutas()
    @Serializable data object Historial : Rutas()
    @Serializable data object Presupuesto : Rutas()
}