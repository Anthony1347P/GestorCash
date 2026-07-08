package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Presupuesto

interface RepositorioPresupuesto {
    suspend fun obtener(mes: String): Result<Presupuesto?>
    suspend fun guardar(mes: String, limite: Double): Result<Presupuesto>
}