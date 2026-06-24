package com.pdm0126.gestorcash_00202124_00073523.screens.Inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.gestorcash_00202124_00073523.model.Movimiento
import com.pdm0126.gestorcash_00202124_00073523.navigation.BarraInferior
import com.pdm0126.gestorcash_00202124_00073523.navigation.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    onNavegar: (Rutas) -> Unit,
    onCerrarSesion: () -> Unit,
    viewModel: InicioViewModel = viewModel()
) {
    val nombre = viewModel.nombre.collectAsState().value
    val movimientos = viewModel.movimientos.collectAsState().value
    val ingresos = viewModel.totalIngresos.collectAsState().value
    val gastos = viewModel.totalGastos.collectAsState().value
    val mayorGasto = viewModel.mayorGasto.collectAsState().value
    val saldo = ingresos - gastos

    LaunchedEffect(Unit) { viewModel.cargar() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (nombre.isBlank()) "Inicio" else "Hola, $nombre") },
                actions = {
                    IconButton(onClick = { viewModel.cerrarSesion(onCerrarSesion) }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesion")
                    }
                }
            )
        },
        bottomBar = { BarraInferior(Rutas.Inicio, onNavegar) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // tarjetas de resumen
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TarjetaResumen("Ingresos", "$" + "%.2f".format(ingresos), Modifier.weight(1f))
                TarjetaResumen("Gastos", "$" + "%.2f".format(gastos), Modifier.weight(1f))
                TarjetaResumen("Saldo", "$" + "%.2f".format(saldo), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Mayor gasto: $mayorGasto",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ultimos movimientos", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(movimientos.take(5), key = { it.id }) { movimiento ->
                    FilaMovimiento(movimiento)
                }
            }
        }
    }
}

@Composable
private fun TarjetaResumen(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titulo, style = MaterialTheme.typography.bodySmall)
            Text(text = valor, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun FilaMovimiento(movimiento: Movimiento) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (movimiento.descripcion.isBlank()) movimiento.categoria else movimiento.descripcion,
            style = MaterialTheme.typography.bodyLarge
        )
        val esIngreso = movimiento.tipo == "ingreso"
        Text(
            text = (if (esIngreso) "+$" else "-$") + "%.2f".format(movimiento.monto),
            color = if (esIngreso) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}