package com.example.recordatoriosapp.presentation

import com.example.recordatoriosapp.model.Recordatorio

sealed interface RecordatoriosUiState {
    data object Cargando : RecordatoriosUiState
    data object Vacio : RecordatoriosUiState
    data class ConDatos(val recordatorios: List<Recordatorio>) : RecordatoriosUiState
    data class Error(val mensaje: String) : RecordatoriosUiState
}
