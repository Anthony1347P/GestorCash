package com.pdm0126.gestorcash_00202124_00073523.data

import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento
import com.pdm0126.gestorcash_00202124_00073523.model.PeticionMovimiento
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class RepositorioMovimientosApi : RepositorioMovimientos {

    private val cliente = ClienteHttp.cliente

    override suspend fun obtenerMovimientos(mes: String): Result<List<Movimiento>> {
        return try {
            val lista: List<Movimiento> = cliente.get("${ClienteHttp.URL_BASE}/movimientos") {
                parameter("mes", mes)
                header(HttpHeaders.Authorization, "Bearer ${ClienteHttp.token ?: ""}")
            }.body()
            Result.success(lista)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun agregar(movimiento: Movimiento): Result<Movimiento> {
        return try {
            val creado: Movimiento = cliente.post("${ClienteHttp.URL_BASE}/movimientos") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${ClienteHttp.token ?: ""}")
                setBody(
                    PeticionMovimiento(
                        tipo = movimiento.tipo,
                        monto = movimiento.monto,
                        categoria = movimiento.categoria,
                        fecha = movimiento.fecha,
                        descripcion = movimiento.descripcion
                    )
                )
            }.body()
            Result.success(creado)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun eliminar(id: Int): Result<Unit> {
        return try {
            cliente.delete("${ClienteHttp.URL_BASE}/movimientos/$id") {
                header(HttpHeaders.Authorization, "Bearer ${ClienteHttp.token ?: ""}")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}