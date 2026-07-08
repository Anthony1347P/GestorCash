package com.pdm0126.gestorcash_00202124_00073523.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

// item de la barra inferior
data class ItemBarra(val ruta: Rutas, val etiqueta: String, val icono: ImageVector)

@Composable
fun BarraInferior(rutaActual: Rutas, onNavegar: (Rutas) -> Unit) {
    val items = listOf(
        ItemBarra(Rutas.Inicio, "Inicio", Icons.Filled.Home),
        ItemBarra(Rutas.Agregar, "Agregar", Icons.Filled.AddCircle),
        ItemBarra(Rutas.Historial, "Historial", Icons.AutoMirrored.Filled.List),
        ItemBarra(Rutas.Presupuesto, "Presupuesto", Icons.Filled.AccountBalanceWallet)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = { onNavegar(item.ruta) },
                icon = { Icon(imageVector = item.icono, contentDescription = item.etiqueta) },
                label = { Text(item.etiqueta) }
            )
        }
    }
}