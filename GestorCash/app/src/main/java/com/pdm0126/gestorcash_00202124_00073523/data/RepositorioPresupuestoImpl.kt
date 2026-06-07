package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Presupuesto

class RepositorioPresupuestoImpl : RepositorioPresupuesto {

    private var presupuesto: Presupuesto? = Presupuesto(1, "2026-06", 400.0)

    override suspend fun obtener(mes: String): Result<Presupuesto?> {
        return Result.success(presupuesto)
    }

    override suspend fun guardar(mes: String, limite: Double): Result<Presupuesto> {
        presupuesto = Presupuesto(1, mes, limite)
        return Result.success(presupuesto!!)
    }
}