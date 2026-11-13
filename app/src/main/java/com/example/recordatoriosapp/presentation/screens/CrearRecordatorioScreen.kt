package com.example.recordatoriosapp.presentation.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.recordatoriosapp.model.Categoria
import com.example.recordatoriosapp.model.Prioridad
import com.example.recordatoriosapp.presentation.CrearRecordatorioUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRecordatorioScreen(
    uiState: CrearRecordatorioUiState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onCategoriaChange: (Categoria) -> Unit,
    onPrioridadChange: (Prioridad) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, topBar = { TopAppBar(title = { Text("Crear recordatorio") }) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.titulo,
                onValueChange = onTituloChange,
                label = { Text("Título") },
                singleLine = true,
                isError = uiState.titulo.isNotEmpty() && uiState.errorTitulo != null,
                supportingText = {
                    Text(if (uiState.titulo.isEmpty()) "Mínimo 4 caracteres" else uiState.errorTitulo ?: "Título válido")
                },
                modifier = Modifier.fillMaxWidth().then(
                    if (uiState.titulo.isNotEmpty() && uiState.errorTitulo != null)
                        Modifier.semantics { error(uiState.errorTitulo!!) }
                    else Modifier
                )
            )
            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("Descripción") },
                minLines = 3,
                isError = uiState.descripcion.isNotEmpty() && uiState.errorDescripcion != null,
                supportingText = {
                    Text(if (uiState.descripcion.isEmpty()) "Obligatoria, máximo 200 caracteres" else uiState.errorDescripcion ?: "${uiState.descripcion.length}/200 caracteres")
                },
                modifier = Modifier.fillMaxWidth().then(
                    if (uiState.descripcion.isNotEmpty() && uiState.errorDescripcion != null)
                        Modifier.semantics { error(uiState.errorDescripcion!!) }
                    else Modifier
                )
            )
            Text("Categoría", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Categoria.entries.forEach { categoria ->
                    FilterChip(
                        selected = uiState.categoria == categoria,
                        onClick = { onCategoriaChange(categoria) },
                        label = { Text(categoria.name) }
                    )
                }
            }
            if (uiState.categoria == null) Text("Seleccioná una categoría", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Prioridad", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Prioridad.entries.forEach { prioridad ->
                    FilterChip(
                        selected = uiState.prioridad == prioridad,
                        onClick = { onPrioridadChange(prioridad) },
                        label = { Text(prioridad.name) }
                    )
                }
            }
            if (uiState.prioridad == null) Text("Seleccioná una prioridad", color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (uiState.errorGeneral != null) {
                Text("Error: ${uiState.errorGeneral}", color = MaterialTheme.colorScheme.error)
            }
            if (uiState.enviando) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Text("Guardando recordatorio...")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancelar, enabled = !uiState.enviando) { Text("Cancelar") }
                Button(onClick = onGuardar, enabled = uiState.puedeGuardar) { Text("Guardar") }
            }
        }
    }
}
