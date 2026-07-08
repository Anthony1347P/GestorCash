package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.PeticionLogin
import com.pdm0126.gestorcash_00202124_00073523.model.PeticionRegistro
import com.pdm0126.gestorcash_00202124_00073523.model.RespuestaAuth
import com.pdm0126.gestorcash_00202124_00073523.model.Usuario
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RepositorioAuthApi : RepositorioAuth {

    private val cliente = ClienteHttp.cliente

    override suspend fun login(correo: String, password: String): Result<Usuario> {
        return try {
            val respuesta: RespuestaAuth = cliente.post("${ClienteHttp.URL_BASE}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(PeticionLogin(correo, password))
            }.body()
            ClienteHttp.token = respuesta.token
            Result.success(respuesta.usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario> {
        return try {
            val respuesta: RespuestaAuth = cliente.post("${ClienteHttp.URL_BASE}/auth/registro") {
                contentType(ContentType.Application.Json)
                setBody(PeticionRegistro(nombre, correo, password))
            }.body()
            ClienteHttp.token = respuesta.token
            Result.success(respuesta.usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}