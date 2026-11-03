
package com.example.recordatoriosapp.model

enum class Categoria {
    ESTUDIO,
    TRABAJO,
    PERSONAL,
    SALUD
}

enum class Prioridad {
    ALTA,
    MEDIA,
    BAJA
}

enum class Estado {
    PENDIENTE,
    EN_PROGRESO,
    COMPLETADO
}

data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: Estado
) {
    init {
        require(id > 0) { "El ID debe ser positivo" }
        require(titulo.isNotBlank()) { "El título no puede estar vacío" }
        require(descripcion.isNotBlank()) { "La descripción no puede estar vacía" }
    }
}


