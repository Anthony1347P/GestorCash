package com.pdm0126.gestorcash_00202124_00073523.screens.Agregar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.gestorcash_00202124_00073523.data.Repositorios
import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AgregarViewModel : ViewModel() {

    private val repositorio = Repositorios.movimientos

    private val _guardando = MutableStateFlow(false)
    val guardando = _guardando.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso = _guardadoExitoso.asStateFlow()

    fun guardar(tipo: String, montoTexto: String, categoria: String, fecha: String, descripcion: String) {
        val monto = montoTexto.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            _mensaje.value = "Ingresa un monto valido"
            return
        }
        if (categoria.isBlank()) {
            _mensaje.value = "Selecciona una categoria"
            return
        }
        viewModelScope.launch {
            _guardando.value = true
            _mensaje.value = null
            val resultado = repositorio.agregar(
                Movimiento(0, tipo, monto, categoria, fecha, descripcion)
            )
            if (resultado.isSuccess) {
                _guardadoExitoso.value = true
            } else {
                _mensaje.value = "No se pudo guardar el movimiento"
            }
            _guardando.value = false
        }
    }

    fun limpiarEstado() {
        _guardadoExitoso.value = false
        _mensaje.value = null
    }
}