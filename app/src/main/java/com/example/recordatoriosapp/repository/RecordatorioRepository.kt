package com.example.recordatoriosapp.repository

import com.example.recordatoriosapp.model.Recordatorio

interface RecordatorioRepository {
    suspend fun obtenerTodos(): List<Recordatorio>
    suspend fun obtenerPorId(id: Int): Recordatorio?
    suspend fun guardar(recordatorio: Recordatorio): Boolean
}
