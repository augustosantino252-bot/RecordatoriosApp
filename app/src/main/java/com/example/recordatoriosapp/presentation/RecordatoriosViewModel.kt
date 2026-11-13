package com.example.recordatoriosapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordatoriosapp.repository.RecordatorioRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecordatoriosViewModel(private val repository: RecordatorioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<RecordatoriosUiState>(RecordatoriosUiState.Cargando)
    val uiState: StateFlow<RecordatoriosUiState> = _uiState

    init { cargarRecordatorios() }

    fun cargarRecordatorios() {
        viewModelScope.launch {
            _uiState.value = RecordatoriosUiState.Cargando
            try {
                val items = repository.obtenerTodos()
                _uiState.value = if (items.isEmpty()) RecordatoriosUiState.Vacio else RecordatoriosUiState.ConDatos(items)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = RecordatoriosUiState.Error(e.message ?: "No se pudieron cargar los recordatorios")
            }
        }
    }

    fun reintentar() = cargarRecordatorios()
}
