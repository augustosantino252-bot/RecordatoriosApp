package com.example.recordatoriosapp.presentation

import com.example.recordatoriosapp.model.Categoria
import com.example.recordatoriosapp.model.Prioridad

data class CrearRecordatorioUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: Categoria? = null,
    val prioridad: Prioridad? = null,
    val enviando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val errorGeneral: String? = null
) {
    val errorTitulo: String?
        get() = when {
            titulo.isBlank() -> "El título es obligatorio"
            titulo.trim().length < 4 -> "El título debe tener al menos 4 caracteres"
            else -> null
        }
    val errorDescripcion: String?
        get() = when {
            descripcion.isBlank() -> "La descripción es obligatoria"
            descripcion.length > 200 -> "La descripción no puede superar los 200 caracteres"
            else -> null
        }
    val errorCategoria: String?
        get() = if (categoria == null) "Seleccioná una categoría" else null
    val errorPrioridad: String?
        get() = if (prioridad == null) "Seleccioná una prioridad" else null
    val formularioValido: Boolean
        get() = errorTitulo == null && errorDescripcion == null && errorCategoria == null && errorPrioridad == null
    val puedeGuardar: Boolean
        get() = formularioValido && !enviando && !guardadoExitoso
}
