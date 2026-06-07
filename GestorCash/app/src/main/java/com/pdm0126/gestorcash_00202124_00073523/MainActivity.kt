package com.pdm0126.gestorcash_00202124_00073523

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdm0126.gestorcash_00202124_00073523.ui.theme.GestorCashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorCashTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text("GestorCash", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}