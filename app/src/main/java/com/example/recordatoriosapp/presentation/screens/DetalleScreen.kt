package com.example.recordatoriosapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recordatoriosapp.presentation.DetalleUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    uiState: DetalleUiState,
    onVolver: () -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, topBar = { TopAppBar(title = { Text("Detalle del recordatorio") }) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onVolver) { Text("← Volver") }
            when (uiState) {
                DetalleUiState.Cargando -> {
                    CircularProgressIndicator()
                    Text("Cargando detalle...")
                }
                is DetalleUiState.Error -> {
                    Text(uiState.mensaje, color = MaterialTheme.colorScheme.error)
                    Button(onClick = onReintentar) { Text("Reintentar") }
                }
                is DetalleUiState.ConDatos -> Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(uiState.recordatorio.titulo, style = MaterialTheme.typography.headlineSmall)
                        Text("Descripción: ${uiState.recordatorio.descripcion}")
                        Text("Categoría: ${uiState.recordatorio.categoria}")
                        Text("Prioridad: ${uiState.recordatorio.prioridad}")
                        Text("Estado: ${uiState.recordatorio.estado}")
                        Text("Identificador: ${uiState.recordatorio.id}")
                    }
                }
            }
        }
    }
}
