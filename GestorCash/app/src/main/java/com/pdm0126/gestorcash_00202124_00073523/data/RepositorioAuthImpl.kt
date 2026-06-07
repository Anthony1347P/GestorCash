package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Usuario

class RepositorioAuthImpl : RepositorioAuth {

    override suspend fun login(correo: String, password: String): Result<Usuario> {
        // datos quemados
        return Result.success(Usuario(1, "Anthony Perez", correo))
    }

    override suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario> {
        return Result.success(Usuario(1, nombre, correo))
    }
}