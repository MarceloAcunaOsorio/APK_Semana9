package com.marceloacuna.myappsemana9.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.marceloacuna.myappsemana9.AuthState
import com.marceloacuna.myappsemana9.AuthViewModel
import com.marceloacuna.myappsemana9.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home (modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(Routes.Login)
            else -> Unit
        }
    }
    columnas(navController)
    TopAppBar(
        title = { Text(text = "My App") },
        colors = TopAppBarDefaults.topAppBarColors(Color.Cyan),
        actions = {

            IconButton(onClick = { Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show() }) {
                Icon(Icons.Default.Home, "")
            }

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.Menu, "")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                // DropdownMenuItem(text = { Text(text = "Crear Letra")}, onClick = {Toast.makeText(context,"Crear Letra",Toast.LENGTH_SHORT).show()})

                DropdownMenuItem(
                    text = { Text(text = "Cerrar Sesión") },
                    onClick = { authViewModel.cerrarSesion() })
            }
        }
    )
}


@Composable
fun columnas(navController: NavController){
    Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpeechRecognitionApp()

        Spacer(modifier = Modifier.height(10.dp))

        crealetra()

        Spacer(modifier = Modifier.height(20.dp))

        RevisaTiempo(navController)

    }
}


@Composable
fun RevisaTiempo(navController: NavController) {
    Button(onClick = { navController.navigate(Routes.MyApp) },shape = RoundedCornerShape(16.dp),modifier = Modifier.size(width = 280.dp, height = 40.dp)) {
        androidx.compose.material.Text(
            "Ver Tiempo",
            style = TextStyle(
                fontSize = 20.sp, // Tamaño de fuente
                fontWeight = FontWeight.Bold // Peso de la fuente //
            )
        )
    }
}














