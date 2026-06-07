package com.novafit.app.ui.screens.miembros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.novafit.app.data.remote.dto.MiembroDto
import com.novafit.app.ui.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiembrosScreen(
    onVolver: () -> Unit,
    viewModel: MiembrosViewModel = hiltViewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val hayMas by viewModel.hayMas.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.cargarMiembros() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Miembros", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        when (val s = estado) {
            is UiState.Cargando -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Error, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(48.dp))
                        Text(s.mensaje, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.cargarMiembros() }) { Text("Reintentar") }
                    }
                }
            }
            is UiState.Exito -> {
                LazyColumn(
                    modifier = Modifier.padding(padding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(s.data) { miembro ->
                        TarjetaMiembro(
                            miembro = miembro,
                            onActivar = { viewModel.activarMiembro(miembro.id) },
                            onDesactivar = { viewModel.desactivarMiembro(miembro.id) }
                        )
                    }
                    if (hayMas) {
                        item {
                            Button(
                                onClick = { viewModel.cargarMas() },
                                modifier = Modifier.fillMaxWidth()
                            ) { Text("Cargar más") }
                        }
                    }
                }
            }
            is UiState.Vacio -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay miembros registrados")
                }
            }
        }
    }
}

@Composable
fun TarjetaMiembro(
    miembro: MiembroDto,
    onActivar: () -> Unit,
    onDesactivar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(miembro.nombreCompleto.ifEmpty { miembro.usuario.username }, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Cédula: ${miembro.cedula}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
                Text("Desde: ${miembro.fechaIngreso}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(0.5f))
                Badge(
                    containerColor = if (miembro.activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                ) {
                    Text(if (miembro.activo) "Activo" else "Inactivo", color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
            Column {
                if (!miembro.activo) {
                    IconButton(onClick = onActivar) {
                        Icon(Icons.Default.CheckCircle, "Activar", tint = MaterialTheme.colorScheme.primary)
                    }
                } else {
                    IconButton(onClick = onDesactivar) {
                        Icon(Icons.Default.Cancel, "Desactivar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
