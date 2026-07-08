package com.pdm0126.gestorcash_00202124_00073523.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.gestorcash_00202124_00073523.data.AlmacenSesion
import com.pdm0126.gestorcash_00202124_00073523.data.ClienteHttp
import com.pdm0126.gestorcash_00202124_00073523.screens.Agregar.AgregarScreen
import com.pdm0126.gestorcash_00202124_00073523.screens.Historial.HistorialScreen
import com.pdm0126.gestorcash_00202124_00073523.screens.Inicio.InicioScreen
import com.pdm0126.gestorcash_00202124_00073523.screens.Login.LoginScreen
import com.pdm0126.gestorcash_00202124_00073523.screens.Presupuesto.PresupuestoScreen
import com.pdm0126.gestorcash_00202124_00073523.screens.Registro.RegistroScreen

@Composable
fun NavegacionPrincipal() {
    val backStack = rememberNavBackStack(Rutas.Login)
    val contexto = LocalContext.current

    // si hay sesion guardada entra directo al inicio
    LaunchedEffect(Unit) {
        val token = AlmacenSesion.obtenerToken(contexto)
        if (token != null) {
            ClienteHttp.token = token
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
            entry<Rutas.Inicio> { _ ->
                InicioScreen(
                    onNavegar = navegarTab,
                    onCerrarSesion = {
                        backStack.clear()
                        backStack.add(Rutas.Login)
                    }
                )
            }
            entry<Rutas.Agregar> { _ -> AgregarScreen(onNavegar = navegarTab) }
            entry<Rutas.Historial> { _ -> HistorialScreen(onNavegar = navegarTab) }
            entry<Rutas.Presupuesto> { _ -> PresupuestoScreen(onNavegar = navegarTab) }
        }
    )
}

