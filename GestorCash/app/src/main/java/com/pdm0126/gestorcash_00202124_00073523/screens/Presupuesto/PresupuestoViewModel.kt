package com.pdm0126.gestorcash_00202124_00073523.screens.Presupuesto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.gestorcash_00202124_00073523.data.Repositorios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PresupuestoViewModel : ViewModel() {

    private val repoPresupuesto = Repositorios.presupuesto
    private val repoMovimientos = Repositorios.movimientos

    private val _limite = MutableStateFlow(0.0)
    val limite = _limite.asStateFlow()

    private val _gastado = MutableStateFlow(0.0)
    val gastado = _gastado.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    fun cargar() {
        viewModelScope.launch {
            val mes = mesActual()
            val resultadoPresupuesto = repoPresupuesto.obtener(mes)
            if (resultadoPresupuesto.isSuccess) {
                _limite.value = resultadoPresupuesto.getOrNull()?.limite ?: 0.0
            }
            val resultadoMovimientos = repoMovimientos.obtenerMovimientos(mes)
            if (resultadoMovimientos.isSuccess) {
                _gastado.value = (resultadoMovimientos.getOrNull() ?: emptyList())
                    .filter { it.tipo == "gasto" }
                    .sumOf { it.monto }
            }
        }
    }

    fun guardarLimite(limiteTexto: String) {
        val nuevoLimite = limiteTexto.toDoubleOrNull()
        if (nuevoLimite == null || nuevoLimite <= 0) {
            _mensaje.value = "Ingresa un limite valido"
            return
        }
        viewModelScope.launch {
            val resultado = repoPresupuesto.guardar(mesActual(), nuevoLimite)
            if (resultado.isSuccess) {
                _limite.value = nuevoLimite
                _mensaje.value = "Limite actualizado"
            } else {
                _mensaje.value = "No se pudo guardar el limite"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    private fun mesActual(): String {
        return SimpleDateFormat("yyyy-MM", Locale.US).format(Date())
    }
}