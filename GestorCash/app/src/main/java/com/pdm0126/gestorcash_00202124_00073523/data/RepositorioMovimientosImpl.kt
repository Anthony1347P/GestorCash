package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento

class RepositorioMovimientosImpl : RepositorioMovimientos {

    private val movimientos = mutableListOf(
        Movimiento(1, "ingreso", 200.0, "Otros", "2026-06-01", "Mesada"),
        Movimiento(2, "gasto", 5.0, "Alimentacion", "2026-06-02", "Almuerzo"),
        Movimiento(3, "gasto", 1.50, "Transporte", "2026-06-02", "Bus"),
        Movimiento(4, "ingreso", 250.0, "Otros", "2026-06-05", "Freelance"),
        Movimiento(5, "gasto", 25.0, "Educacion", "2026-06-06", "Libros"),
        Movimiento(6, "gasto", 12.0, "Alimentacion", "2026-06-08", "Cena"),
        Movimiento(7, "gasto", 8.0, "Entretenimiento", "2026-06-10", "Netflix"),
        Movimiento(8, "gasto", 15.0, "Gasolina", "2026-06-12", "Gasolina")
    )

    override suspend fun obtenerMovimientos(mes: String): Result<List<Movimiento>> {
        return Result.success(movimientos.filter { it.fecha.startsWith(mes) })
    }

    override suspend fun agregar(movimiento: Movimiento): Result<Movimiento> {
        val nuevoId = (movimientos.maxOfOrNull { it.id } ?: 0) + 1
        val nuevo = movimiento.copy(id = nuevoId)
        movimientos.add(nuevo)
        return Result.success(nuevo)
    }

    override suspend fun eliminar(id: Int): Result<Unit> {
        movimientos.removeAll { it.id == id }
        return Result.success(Unit)
    }
}