package com.example.recordatoriosapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recordatoriosapp.model.Recordatorio
import com.example.recordatoriosapp.presentation.RecordatoriosUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(
    uiState: RecordatoriosUiState,
    onCrearRecordatorio: () -> Unit,
    onRecordatorioClick: (Int) -> Unit,
    onReintentar: () -> Unit,
    onSimularVacio: () -> Unit,
    onSimularError: () -> Unit,
    onRestaurarDatos: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("Mis recordatorios") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearRecordatorio) { Text("+ Nuevo") }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text("Modo de prueba (para evidencias):", modifier = Modifier.padding(start = 16.dp, top = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onRestaurarDatos) { Text("Con datos") }
                TextButton(onClick = onSimularVacio) { Text("Vacío") }
                TextButton(onClick = onSimularError) { Text("Error") }
            }
            when (uiState) {
                RecordatoriosUiState.Cargando -> EstadoMensaje("Cargando recordatorios...", cargando = true)
                RecordatoriosUiState.Vacio -> EstadoMensaje(
                    "Todavía no existen recordatorios.",
                    textoBoton = "Crear recordatorio", onAccion = onCrearRecordatorio
                )
                is RecordatoriosUiState.Error -> EstadoMensaje(
                    uiState.mensaje, textoBoton = "Reintentar", onAccion = onReintentar
                )
                is RecordatoriosUiState.ConDatos -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.recordatorios, key = { it.id }) { recordatorio ->
                        RecordatorioCard(recordatorio, onClick = { onRecordatorioClick(recordatorio.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun RecordatorioCard(recordatorio: Recordatorio, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(recordatorio.titulo, style = MaterialTheme.typography.titleMedium)
            Text("Categoría: ${recordatorio.categoria}")
            Text("Prioridad: ${recordatorio.prioridad}")
            Text("Estado: ${recordatorio.estado}")
        }
    }
}

@Composable
fun EstadoMensaje(
    mensaje: String,
    modifier: Modifier = Modifier,
    textoBoton: String? = null,
    onAccion: () -> Unit = {},
    cargando: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cargando) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
        }
        Text(mensaje, style = MaterialTheme.typography.bodyLarge)
        if (textoBoton != null) {
            Spacer(Modifier.height(16.dp))
            Button(onClick = onAccion) { Text(textoBoton) }
        }
    }
}
