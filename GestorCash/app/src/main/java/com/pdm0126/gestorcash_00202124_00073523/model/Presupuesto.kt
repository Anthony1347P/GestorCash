package com.pdm0126.gestorcash_00202124_00073523.model

import kotlinx.serialization.Serializable

@Serializable
data class Presupuesto(
    val id: Int,
    val mes: String,         // formato "2026-06"
    val limite: Double
)