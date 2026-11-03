
package com.example.recordatoriosapp.model

object DatosRecordatorios {

    val recordatorios = listOf(
        Recordatorio(
            1,
            "Estudiar Kotlin",
            "Repasar conceptos de programación",
            Categoria.ESTUDIO,
            Prioridad.ALTA,
            Estado.PENDIENTE
        ),
        Recordatorio(
            2,
            "Comprar alimentos",
            "Comprar frutas y verduras",
            Categoria.PERSONAL,
            Prioridad.MEDIA,
            Estado.PENDIENTE
        ),
        Recordatorio(
            3,
            "Entregar actividad",
            "Finalizar la actividad de programación mobile",
            Categoria.ESTUDIO,
            Prioridad.ALTA,
            Estado.EN_PROGRESO
        ),
        Recordatorio(
            4,
            "Hacer ejercicio",
            "Realizar entrenamiento diario",
            Categoria.SALUD,
            Prioridad.BAJA,
            Estado.COMPLETADO
        )
    )

    // Set: obtiene categorías sin repetir
    val categorias: Set<Categoria> =
        recordatorios.map { it.categoria }.toSet()

    // Map: asocia cada categoría con un color
    val coloresCategorias: Map<Categoria, Long> = mapOf(
        Categoria.ESTUDIO to 0xFF2196F3,
        Categoria.TRABAJO to 0xFFFF9800,
        Categoria.PERSONAL to 0xFF4CAF50,
        Categoria.SALUD to 0xFFE91E63
    )

    // Agrupa los recordatorios por categoría
    val agrupados = recordatorios.groupBy { it.categoria }

    // Filtra recordatorios pendientes
    val pendientes = recordatorios.filter {
        it.estado == Estado.PENDIENTE
    }

    // Filtra recordatorios de prioridad alta
    val prioridadAlta = recordatorios.filter {
        it.prioridad == Prioridad.ALTA
    }
}
