package com.example.recordatoriosapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordatoriosapp.model.Categoria
import com.example.recordatoriosapp.model.Estado
import com.example.recordatoriosapp.model.Prioridad
import com.example.recordatoriosapp.model.Recordatorio
import com.example.recordatoriosapp.repository.RecordatorioRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrearRecordatorioViewModel(private val repository: RecordatorioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CrearRecordatorioUiState())
    val uiState: StateFlow<CrearRecordatorioUiState> = _uiState

    fun cambiarTitulo(valor: String) { _uiState.update { it.copy(titulo = valor, errorGeneral = null) } }
    fun cambiarDescripcion(valor: String) { _uiState.update { it.copy(descripcion = valor, errorGeneral = null) } }
    fun cambiarCategoria(valor: Categoria) { _uiState.update { it.copy(categoria = valor, errorGeneral = null) } }
    fun cambiarPrioridad(valor: Prioridad) { _uiState.update { it.copy(prioridad = valor, errorGeneral = null) } }

    fun guardar() {
        val estado = _uiState.value
        if (!estado.puedeGuardar) return
        val categoria = estado.categoria ?: return
        val prioridad = estado.prioridad ?: return
        _uiState.update { it.copy(enviando = true, errorGeneral = null) }
        viewModelScope.launch {
            try {
                val existentes = repository.obtenerTodos()
                val nuevo = Recordatorio(
                    id = (existentes.maxOfOrNull { it.id } ?: 0) + 1,
                    titulo = estado.titulo.trim(),
                    descripcion = estado.descripcion.trim(),
                    categoria = categoria,
                    prioridad = prioridad,
                    estado = Estado.PENDIENTE
                )
                val resultado = repository.guardar(nuevo)
                _uiState.update {
                    it.copy(
                        enviando = false,
                        guardadoExitoso = resultado,
                        errorGeneral = if (resultado) null else "No se pudo guardar el recordatorio"
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(enviando = false, errorGeneral = e.message ?: "Error al guardar") }
            }
        }
    }
}
