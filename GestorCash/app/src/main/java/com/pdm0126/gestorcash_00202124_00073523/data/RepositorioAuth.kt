package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Usuario

interface RepositorioAuth {
    suspend fun login(correo: String, password: String): Result<Usuario>
    suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario>
}