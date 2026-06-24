package com.pdm0126.gestorcash_00202124_00073523.screens.Inicio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.gestorcash_00202124_00073523.data.AlmacenSesion
import com.pdm0126.gestorcash_00202124_00073523.data.Repositorios
import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InicioViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio = Repositorios.movimientos

    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    private val _movimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val movimientos = _movimientos.asStateFlow()

    private val _totalIngresos = MutableStateFlow(0.0)
    val totalIngresos = _totalIngresos.asStateFlow()

    private val _totalGastos = MutableStateFlow(0.0)
    val totalGastos = _totalGastos.asStateFlow()

    private val _mayorGasto = MutableStateFlow("Ninguno")
    val mayorGasto = _mayorGasto.asStateFlow()

    fun cargar() {
        viewModelScope.launch {
            _nombre.value = AlmacenSesion.obtenerNombre(getApplication()) ?: ""
            val mes = SimpleDateFormat("yyyy-MM", Locale.US).format(Date())
            val resultado = repositorio.obtenerMovimientos(mes)
            if (resultado.isSuccess) {
                val lista = resultado.getOrNull() ?: emptyList()
                _movimientos.value = lista
                _totalIngresos.value = lista.filter { it.tipo == "ingreso" }.sumOf { it.monto }
                _totalGastos.value = lista.filter { it.tipo == "gasto" }.sumOf { it.monto }
                _mayorGasto.value = lista.filter { it.tipo == "gasto" }
                    .groupBy { it.categoria }
                    .mapValues { entrada -> entrada.value.sumOf { it.monto } }
                    .maxByOrNull { it.value }?.key ?: "Ninguno"
            }
        }
    }

    fun cerrarSesion(alTerminar: () -> Unit) {
        viewModelScope.launch {
            AlmacenSesion.cerrar(getApplication())
            alTerminar()
        }
    }
}