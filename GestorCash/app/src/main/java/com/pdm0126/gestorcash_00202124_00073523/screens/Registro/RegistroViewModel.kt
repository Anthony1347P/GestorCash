package com.pdm0126.gestorcash_00202124_00073523.screens.Registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.gestorcash_00202124_00073523.data.RepositorioAuth
import com.pdm0126.gestorcash_00202124_00073523.data.RepositorioAuthImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {

    private val repositorio: RepositorioAuth = RepositorioAuthImpl()

    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError = _mensajeError.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso = _registroExitoso.asStateFlow()

    fun registrar(nombre: String, correo: String, password: String, confirmar: String) {
        if (nombre.isBlank() || correo.isBlank() || password.isBlank() || confirmar.isBlank()) {
            _mensajeError.value = "Todos los campos son obligatorios"
            return
        }
        if (password != confirmar) {
            _mensajeError.value = "Las contrasenas no coinciden"
            return
        }
        viewModelScope.launch {
            _cargando.value = true
            _mensajeError.value = null
            val resultado = repositorio.registrar(nombre, correo, password)
            if (resultado.isSuccess) {
                _registroExitoso.value = true
            } else {
                _mensajeError.value = "No se pudo crear la cuenta"
            }
            _cargando.value = false
        }
    }
}