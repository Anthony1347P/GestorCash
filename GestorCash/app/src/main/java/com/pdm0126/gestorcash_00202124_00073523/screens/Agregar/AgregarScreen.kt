package com.pdm0126.gestorcash_00202124_00073523.screens.Agregar

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.gestorcash_00202124_00073523.model.CATEGORIAS
import com.pdm0126.gestorcash_00202124_00073523.navigation.BarraInferior
import com.pdm0126.gestorcash_00202124_00073523.navigation.Rutas
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarScreen(
    onNavegar: (Rutas) -> Unit,
    viewModel: AgregarViewModel = viewModel()
) {
    var tipo by remember { mutableStateOf("gasto") }
    var monto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var expandido by remember { mutableStateOf(false) }
    val fechaHoy = remember { SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()) }
    var fecha by remember { mutableStateOf(fechaHoy) }
    var descripcion by remember { mutableStateOf("") }

    val guardando = viewModel.guardando.collectAsState().value
    val mensaje = viewModel.mensaje.collectAsState().value
    val guardadoExitoso = viewModel.guardadoExitoso.collectAsState().value
    val contexto = LocalContext.current

    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso) {
            Toast.makeText(contexto, "Movimiento guardado", Toast.LENGTH_SHORT).show()
            tipo = "gasto"
            monto = ""
            categoria = ""
            descripcion = ""
            fecha = fechaHoy
            viewModel.limpiarEstado()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Agregar Movimiento") }) },
        bottomBar = { BarraInferior(Rutas.Agregar, onNavegar) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Tipo de movimiento", style = MaterialTheme.typography.titleMedium)
            Row {
                FilterChip(
                    selected = tipo == "ingreso",
                    onClick = { tipo = "ingreso" },
                    label = { Text("Ingreso") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = tipo == "gasto",
                    onClick = { tipo = "gasto" },
                    label = { Text("Gasto") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto ($)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text("Categoria", style = MaterialTheme.typography.titleMedium)
            Box {
                OutlinedButton(
                    onClick = { expandido = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (categoria.isBlank()) "Seleccionar categoria" else categoria)
                }
                DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                    CATEGORIAS.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                categoria = opcion
                                expandido = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (yyyy-mm-dd)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            if (mensaje != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensaje, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.guardar(tipo, monto, categoria, fecha, descripcion) },
                enabled = !guardando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (guardando) "Guardando..." else "Guardar movimiento")
            }
        }
    }
}