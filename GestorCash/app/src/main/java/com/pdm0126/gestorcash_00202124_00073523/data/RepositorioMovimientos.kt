package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento

interface RepositorioMovimientos {
    suspend fun obtenerMovimientos(mes: String): Result<List<Movimiento>>
    suspend fun agregar(movimiento: Movimiento): Result<Movimiento>
    suspend fun eliminar(id: Int): Result<Unit>
}