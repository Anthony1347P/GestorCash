package com.pdm0126.gestorcash_00202124_00073523

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pdm0126.gestorcash_00202124_00073523.navigation.NavegacionPrincipal
import com.pdm0126.gestorcash_00202124_00073523.ui.theme.GestorCashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorCashTheme {
                NavegacionPrincipal()
            }
        }
    }
}