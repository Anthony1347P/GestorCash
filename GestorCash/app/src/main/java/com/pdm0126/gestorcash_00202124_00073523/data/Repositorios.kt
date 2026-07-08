package com.pdm0126.gestorcash_00202124_00073523.data

// instancia unica de cada repositorio para compartir datos entre pantallas
object Repositorios {
    val auth: RepositorioAuth = RepositorioAuthApi()
    val movimientos: RepositorioMovimientos = RepositorioMovimientosApi()
    val presupuesto: RepositorioPresupuesto = RepositorioPresupuestoApi()
}