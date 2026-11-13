package com.example.recordatoriosapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordatoriosapp.model.Recordatorio
import com.example.recordatoriosapp.repository.RecordatorioRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DetalleUiState {
    data object Cargando : DetalleUiState
    data class ConDatos(val recordatorio: Recordatorio) : DetalleUiState
    data class Error(val mensaje: String) : DetalleUiState
}

class DetalleRecordatorioViewModel(private val repository: RecordatorioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)
    val uiState: StateFlow<DetalleUiState> = _uiState

    fun cargar(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetalleUiState.Cargando
            try {
                val recordatorio = repository.obtenerPorId(id)
                _uiState.value = if (recordatorio == null) {
                    DetalleUiState.Error("No se encontró el recordatorio")
                } else {
                    DetalleUiState.ConDatos(recordatorio)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = DetalleUiState.Error(e.message ?: "Error al cargar detalle")
            }
        }
    }
}
