package com.pdm0126.gestorcash_00202124_00073523.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ClienteHttp {

    // 10.0.2.2 apunta al localhost de la computadora desde el emulador
    const val URL_BASE = "http://100.73.203.18:3000"
    // token de la sesion activa para las peticiones protegidas
    var token: String? = null

    val cliente = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }
}