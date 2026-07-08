package com.pdm0126.gestorcash_00202124_00073523.screens.Login

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
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio: RepositorioAuth = Repositorios.auth
    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError = _mensajeError.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso = _loginExitoso.asStateFlow()

    fun login(correo: String, password: String) {
        if (correo.isBlank() || password.isBlank()) {
            _mensajeError.value = "Todos los campos son obligatorios"
            return
        }
        viewModelScope.launch {
            _cargando.value = true
            _mensajeError.value = null
            val resultado = repositorio.login(correo, password)
            val usuario = resultado.getOrNull()
            if (resultado.isSuccess && usuario != null) {
                // guarda la sesion para mantenerla activa

                AlmacenSesion.guardar(getApplication(), ClienteHttp.token ?: "", usuario.nombre)
                _loginExitoso.value = true
            } else {
                _mensajeError.value = "Credenciales incorrectas"
            }
            _cargando.value = false
        }
    }
}

