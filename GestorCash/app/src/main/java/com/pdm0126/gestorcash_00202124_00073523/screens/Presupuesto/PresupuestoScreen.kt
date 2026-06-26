package com.pdm0126.gestorcash_00202124_00073523.screens.Presupuesto

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.gestorcash_00202124_00073523.navigation.BarraInferior
import com.pdm0126.gestorcash_00202124_00073523.navigation.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoScreen(
    onNavegar: (Rutas) -> Unit,
    viewModel: PresupuestoViewModel = viewModel()
) {
    var nuevoLimite by remember { mutableStateOf("") }

    val limite = viewModel.limite.collectAsState().value
    val gastado = viewModel.gastado.collectAsState().value
    val mensaje = viewModel.mensaje.collectAsState().value
    val contexto = LocalContext.current

    val disponible = limite - gastado
    val progreso = if (limite > 0) (gastado / limite).toFloat().coerceIn(0f, 1f) else 0f
    val porcentaje = if (limite > 0) ((gastado / limite) * 100).toInt() else 0

    // color de la barra segun el nivel de gasto
    val colorBarra = when {
        porcentaje >= 100 -> MaterialTheme.colorScheme.error
        porcentaje >= 70 -> Color(0xFFF59E0B)
        else -> MaterialTheme.colorScheme.primary
    }

    LaunchedEffect(Unit) { viewModel.cargar() }

    LaunchedEffect(mensaje) {
        if (mensaje == "Limite actualizado") {
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show()
            nuevoLimite = ""
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Presupuesto") }) },
        bottomBar = { BarraInferior(Rutas.Presupuesto, onNavegar) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Presupuesto del mes",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Limite definido: ",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$" + "%.2f".format(limite),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "Gastado: $" + "%.2f".format(gastado),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Disponible: $" + "%.2f".format(disponible),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = { progreso },
                        color = colorBarra,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$porcentaje % de $" + "%.2f".format(limite),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Actualizar limite", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nuevoLimite,
                onValueChange = { nuevoLimite = it },
                label = { Text("Nuevo limite ($)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            if (mensaje != null && mensaje != "Limite actualizado") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensaje, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.guardarLimite(nuevoLimite) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar limite")
            }
        }
    }
}