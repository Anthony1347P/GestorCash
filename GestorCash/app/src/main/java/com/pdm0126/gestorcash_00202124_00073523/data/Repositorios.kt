package com.pdm0126.gestorcash_00202124_00073523.data

// instancia unica de cada repositorio para compartir datos entre pantallas
object Repositorios {
    val auth: RepositorioAuth = RepositorioAuthImpl()
    val movimientos: RepositorioMovimientos = RepositorioMovimientosImpl()
    val presupuesto: RepositorioPresupuesto = RepositorioPresupuestoImpl()
}