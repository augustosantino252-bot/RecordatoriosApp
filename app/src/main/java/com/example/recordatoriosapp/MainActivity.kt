package com.example.recordatoriosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recordatoriosapp.presentation.CrearRecordatorioViewModel
import com.example.recordatoriosapp.presentation.DetalleRecordatorioViewModel
import com.example.recordatoriosapp.presentation.RecordatoriosViewModel
import com.example.recordatoriosapp.presentation.screens.CrearRecordatorioScreen
import com.example.recordatoriosapp.presentation.screens.DetalleScreen
import com.example.recordatoriosapp.presentation.screens.ListadoScreen
import com.example.recordatoriosapp.repository.InMemoryRecordatorioRepository
import com.example.recordatoriosapp.repository.ModoDemostracion
import com.example.recordatoriosapp.ui.theme.RecordatoriosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecordatoriosAppTheme { RecordatoriosAppNavegacion() }
        }
    }
}

@Composable
private fun RecordatoriosAppNavegacion() {
    val navController = rememberNavController()
    val repository = remember { InMemoryRecordatorioRepository() }
    val listadoViewModel: RecordatoriosViewModel = viewModel(
        factory = viewModelFactory { initializer { RecordatoriosViewModel(repository) } }
    )
    NavHost(navController = navController, startDestination = "listado") {
        composable("listado") {
            val estado = listadoViewModel.uiState.collectAsStateWithLifecycle().value
            ListadoScreen(
                uiState = estado,
                onCrearRecordatorio = { navController.navigate("crear") },
                onRecordatorioClick = { id -> navController.navigate("detalle/$id") },
                onReintentar = {
                    repository.modoDemostracion = ModoDemostracion.NORMAL
                    listadoViewModel.reintentar()
                },
                onSimularVacio = {
                    repository.modoDemostracion = ModoDemostracion.VACIO
                    listadoViewModel.cargarRecordatorios()
                },
                onSimularError = {
                    repository.modoDemostracion = ModoDemostracion.ERROR
                    listadoViewModel.cargarRecordatorios()
                },
                onRestaurarDatos = {
                    repository.modoDemostracion = ModoDemostracion.NORMAL
                    listadoViewModel.cargarRecordatorios()
                }
            )
        }
        composable("crear") {
            val crearViewModel: CrearRecordatorioViewModel = viewModel(
                factory = viewModelFactory { initializer { CrearRecordatorioViewModel(repository) } }
            )
            val estado = crearViewModel.uiState.collectAsStateWithLifecycle().value
            LaunchedEffect(estado.guardadoExitoso) {
                if (estado.guardadoExitoso) {
                    repository.modoDemostracion = ModoDemostracion.NORMAL
                    listadoViewModel.cargarRecordatorios()
                    navController.popBackStack()
                }
            }
            CrearRecordatorioScreen(
                uiState = estado,
                onTituloChange = crearViewModel::cambiarTitulo,
                onDescripcionChange = crearViewModel::cambiarDescripcion,
                onCategoriaChange = crearViewModel::cambiarCategoria,
                onPrioridadChange = crearViewModel::cambiarPrioridad,
                onGuardar = crearViewModel::guardar,
                onCancelar = { navController.popBackStack() }
            )
        }
        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: -1
            val detalleViewModel: DetalleRecordatorioViewModel = viewModel(
                factory = viewModelFactory { initializer { DetalleRecordatorioViewModel(repository) } }
            )
            val estado = detalleViewModel.uiState.collectAsStateWithLifecycle().value
            LaunchedEffect(id) { detalleViewModel.cargar(id) }
            DetalleScreen(
                uiState = estado,
                onVolver = { navController.popBackStack() },
                onReintentar = { detalleViewModel.cargar(id) }
            )
        }
    }
}
