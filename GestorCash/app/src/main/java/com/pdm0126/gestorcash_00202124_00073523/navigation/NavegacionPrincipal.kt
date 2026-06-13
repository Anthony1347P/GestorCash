package com.pdm0126.gestorcash_00202124_00073523.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.gestorcash_00202124_00073523.data.AlmacenSesion
import com.pdm0126.gestorcash_00202124_00073523.screens.Login.LoginScreen
import com.pdm0126.gestorcash_00202124_00073523.screens.Registro.RegistroScreen

@Composable
fun NavegacionPrincipal() {
    val backStack = rememberNavBackStack(Rutas.Login)
    val contexto = LocalContext.current

    // si hay sesion guardada entra directo al inicio
    LaunchedEffect(Unit) {
        val token = AlmacenSesion.obtenerToken(contexto)
        if (token != null) {
            backStack.clear()
            backStack.add(Rutas.Inicio)
        }
    }

    // cambia de tab reemplazando la pantalla actual
    val navegarTab: (Rutas) -> Unit = { ruta ->
        if (backStack.lastOrNull() != ruta) {
            backStack.removeLastOrNull()
            backStack.add(ruta)
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Rutas.Login> { _ ->
                LoginScreen(
                    onLoginExitoso = {
                        backStack.clear()
                        backStack.add(Rutas.Inicio)
                    },
                    onIrARegistro = { backStack.add(Rutas.Registro) }
                )
            }
            entry<Rutas.Registro> { _ ->
                RegistroScreen(
                    onRegistroExitoso = {
                        backStack.clear()
                        backStack.add(Rutas.Inicio)
                    },
                    onVolverLogin = { backStack.removeLastOrNull() }
                )
            }
            entry<Rutas.Inicio> { _ -> PantallaTab("Inicio", Rutas.Inicio, navegarTab) }
            entry<Rutas.Agregar> { _ -> PantallaTab("Agregar", Rutas.Agregar, navegarTab) }
            entry<Rutas.Historial> { _ -> PantallaTab("Historial", Rutas.Historial, navegarTab) }
            entry<Rutas.Presupuesto> { _ -> PantallaTab("Presupuesto", Rutas.Presupuesto, navegarTab) }
        }
    )
}

@Composable
fun PantallaTab(nombre: String, ruta: Rutas, onNavegar: (Rutas) -> Unit) {
    Scaffold(bottomBar = { BarraInferior(ruta, onNavegar) }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Pantalla $nombre")
        }
    }
}