package com.example.recordatoriosapp.repository

import com.example.recordatoriosapp.model.DatosRecordatorios
import com.example.recordatoriosapp.model.Recordatorio
import kotlinx.coroutines.delay

enum class ModoDemostracion { NORMAL, VACIO, ERROR }

class InMemoryRecordatorioRepository : RecordatorioRepository {
    private val recordatorios = DatosRecordatorios.recordatorios.toMutableList()
    var modoDemostracion: ModoDemostracion = ModoDemostracion.NORMAL

    override suspend fun obtenerTodos(): List<Recordatorio> {
        delay(500)
        return when (modoDemostracion) {
            ModoDemostracion.NORMAL -> recordatorios.toList()
            ModoDemostracion.VACIO -> emptyList()
            ModoDemostracion.ERROR -> throw IllegalStateException("Error simulado al cargar recordatorios")
        }
    }

    override suspend fun obtenerPorId(id: Int): Recordatorio? {
        delay(200)
        return recordatorios.find { it.id == id }
    }

    override suspend fun guardar(recordatorio: Recordatorio): Boolean {
        delay(800)
        if (modoDemostracion == ModoDemostracion.ERROR) {
            throw IllegalStateException("Error simulado al guardar el recordatorio")
        }
        if (recordatorios.any { it.id == recordatorio.id }) return false
        recordatorios.add(recordatorio)
        return true
    }
}
