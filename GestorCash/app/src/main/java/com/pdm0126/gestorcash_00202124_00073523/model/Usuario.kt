package com.pdm0126.gestorcash_00202124_00073523.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String
)