package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.PeticionPresupuesto
import com.pdm0126.gestorcash_00202124_00073523.model.Presupuesto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class RepositorioPresupuestoApi : RepositorioPresupuesto {

    private val cliente = ClienteHttp.cliente

    override suspend fun obtener(mes: String): Result<Presupuesto?> {
        return try {
            val presupuesto: Presupuesto = cliente.get("${ClienteHttp.URL_BASE}/presupuesto") {
                parameter("mes", mes)
                header(HttpHeaders.Authorization, "Bearer ${ClienteHttp.token ?: ""}")
            }.body()
            Result.success(presupuesto)
        } catch (e: Exception) {
            // si el backend responde 404 significa que aun no hay presupuesto para el mes
            Result.success(null)
        }
    }

    override suspend fun guardar(mes: String, limite: Double): Result<Presupuesto> {
        return try {
            val guardado: Presupuesto = cliente.post("${ClienteHttp.URL_BASE}/presupuesto") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${ClienteHttp.token ?: ""}")
                setBody(PeticionPresupuesto(mes, limite))
            }.body()
            Result.success(guardado)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}