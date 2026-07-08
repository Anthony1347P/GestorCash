package com.pdm0126.gestorcash_00202124_00073523.model

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaAuth(
    val token: String,
    val usuario: Usuario
)

@Serializable
data class PeticionLogin(
    val correo: String,
    val password: String
)

@Serializable
data class PeticionRegistro(
    val nombre: String,
    val correo: String,
    val password: String
)

@Serializable
data class PeticionMovimiento(
    val tipo: String,
    val monto: Double,
    val categoria: String,
    val fecha: String,
    val descripcion: String
)

@Serializable
data class PeticionPresupuesto(
    val mes: String,
    val limite: Double
)