package com.novafit.app.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.novafit.app.ui.navigation.Pantalla

data class MenuOpcion(
    val titulo: String,
    val icono: ImageVector,
    val ruta: String,
    val descripcion: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavegar: (String) -> Unit,
    onCerrarSesion: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val username by viewModel.username.collectAsStateWithLifecycle(initialValue = "")
    var mostrarDialogo by remember { mutableStateOf(false) }

    val opciones = listOf(
        MenuOpcion("Miembros", Icons.Default.People, Pantalla.Miembros.ruta, "Gestionar miembros"),
        MenuOpcion("Planes", Icons.Default.CardMembership, Pantalla.Planes.ruta, "Planes de membresía"),
        MenuOpcion("Membresías", Icons.Default.Assignment, Pantalla.Membresias.ruta, "Gestionar membresías"),
        MenuOpcion("Pagos", Icons.Default.Payments, Pantalla.Pagos.ruta, "Control de pagos"),
        MenuOpcion("Entrenadores", Icons.Default.SportsGymnastics, Pantalla.Entrenadores.ruta, "Personal entrenador"),
        MenuOpcion("Clases", Icons.Default.FitnessCenter, Pantalla.Clases.ruta, "Horario de clases"),
        MenuOpcion("Asistencias", Icons.Default.CheckCircle, Pantalla.Asistencias.ruta, "Control de acceso"),
    )

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.cerrarSesion()
                    onCerrarSesion()
                }) { Text("Sí, salir") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("NovaFit", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Hola, ${username ?: "Usuario"}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        Icons.Default.FitnessCenter,
                        null,
                        modifier = Modifier.padding(start = 8.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { mostrarDialogo = true }) {
                        Icon(Icons.Default.Logout, "Cerrar sesión", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Panel de Control",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(opciones) { opcion ->
                    TarjetaMenu(opcion = opcion, onClick = { onNavegar(opcion.ruta) })
                }
            }
        }
    }
}

@Composable
fun TarjetaMenu(opcion: MenuOpcion, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = opcion.icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = opcion.titulo,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 14.sp
            )
        }
    }
}
