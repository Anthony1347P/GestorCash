package com.pdm0126.gestorcash_00202124_00073523.screens.Historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.gestorcash_00202124_00073523.data.Repositorios
import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HistorialViewModel : ViewModel() {

    private val repositorio = Repositorios.movimientos

    private val _mes = MutableStateFlow(mesActual())
    val mes = _mes.asStateFlow()

    private val _movimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val movimientos = _movimientos.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()

    fun cargar() {
        viewModelScope.launch {
            _cargando.value = true
            val resultado = repositorio.obtenerMovimientos(_mes.value)
            if (resultado.isSuccess) {
                _movimientos.value = resultado.getOrNull() ?: emptyList()
            }
            _cargando.value = false
        }
    }

    fun mesAnterior() {
        _mes.value = sumarMeses(_mes.value, -1)
        cargar()
    }

    fun mesSiguiente() {
        _mes.value = sumarMeses(_mes.value, 1)
        cargar()
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            repositorio.eliminar(id)
            cargar()
        }
    }

    private fun mesActual(): String {
        return SimpleDateFormat("yyyy-MM", Locale.US).format(Date())
    }

    private fun sumarMeses(mes: String, cantidad: Int): String {
        val formato = SimpleDateFormat("yyyy-MM", Locale.US)
        val calendario = Calendar.getInstance()
        calendario.time = formato.parse(mes) ?: Date()
        calendario.add(Calendar.MONTH, cantidad)
        return formato.format(calendario.time)
    }
}