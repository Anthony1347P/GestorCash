package com.pdm0126.gestorcash_00202124_00073523.screens.Historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
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
fun HistorialScreen(
    onNavegar: (Rutas) -> Unit,
    viewModel: HistorialViewModel = viewModel()
) {
    val mes = viewModel.mes.collectAsState().value
    val movimientos = viewModel.movimientos.collectAsState().value

    // recarga cada vez que se entra a la pantalla
    LaunchedEffect(Unit) { viewModel.cargar() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Historial") }) },
        bottomBar = { BarraInferior(Rutas.Historial, onNavegar) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // selector de mes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.mesAnterior() }) {
                    Icon(Icons.Filled.ChevronLeft, contentDescription = "Mes anterior")
                }
                Text(text = mes, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { viewModel.mesSiguiente() }) {
                    Icon(Icons.Filled.ChevronRight, contentDescription = "Mes siguiente")
                }
            }

            if (movimientos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay movimientos en este mes")
                }
            } else {
                LazyColumn {
                    items(movimientos, key = { it.id }) { movimiento ->
                        ItemMovimiento(
                            movimiento = movimiento,
                            onEliminar = { viewModel.eliminar(movimiento.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemMovimiento(movimiento: Movimiento, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (movimiento.descripcion.isBlank()) movimiento.categoria else movimiento.descripcion,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${movimiento.categoria} - ${movimiento.fecha}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            val esIngreso = movimiento.tipo == "ingreso"
            Text(
                text = (if (esIngreso) "+$" else "-$") + "%.2f".format(movimiento.monto),
                color = if (esIngreso) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = onEliminar) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
            }
        }
    }
}