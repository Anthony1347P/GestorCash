package com.pdm0126.gestorcash_00202124_00073523.model

import kotlinx.serialization.Serializable

@Serializable
data class Movimiento(
    val id: Int,
    val tipo: String,        // "ingreso" o "gasto"
    val monto: Double,
    val categoria: String,
    val fecha: String,       // formato "2026-06-13"
    val descripcion: String = ""
)