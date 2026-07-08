package com.pdm0126.gestorcash_00202124_00073523.screens.Registro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.gestorcash_00202124_00073523.data.AlmacenSesion
import com.pdm0126.gestorcash_00202124_00073523.data.RepositorioAuth
import com.pdm0126.gestorcash_00202124_00073523.data.Repositorios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.pdm0126.gestorcash_00202124_00073523.data.ClienteHttp

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio: RepositorioAuth = Repositorios.auth
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
            val usuario = resultado.getOrNull()
            if (resultado.isSuccess && usuario != null) {
                AlmacenSesion.guardar(getApplication(), ClienteHttp.token ?: "", usuario.nombre)
                _registroExitoso.value = true
            } else {
                _mensajeError.value = "No se pudo crear la cuenta"
            }
            _cargando.value = false
        }
    }
}

