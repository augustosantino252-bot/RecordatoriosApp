
package com.example.recordatoriosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordatoriosapp.model.*
import com.example.recordatoriosapp.ui.theme.RecordatoriosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecordatoriosAppTheme {
                PantallaRecordatorios()
            }
        }
    }
}

@Composable
fun PantallaRecordatorios() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Mis recordatorios",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Organizá tus tareas del día",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(DatosRecordatorios.recordatorios) { recordatorio ->
                    TarjetaRecordatorio(recordatorio)
                }
            }
        }
    }
}

@Composable
fun TarjetaRecordatorio(recordatorio: Recordatorio) {
    val colorCategoria = Color(
        DatosRecordatorios.coloresCategorias[recordatorio.categoria]
            ?: 0xFF9E9E9E
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = recordatorio.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = recordatorio.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Categoría: ${recordatorio.categoria}",
                color = colorCategoria
            )

            Text(
                text = "Prioridad: ${recordatorio.prioridad}"
            )

            Text(
                text = "Estado: ${recordatorio.estado}"
            )
        }
    }
}
