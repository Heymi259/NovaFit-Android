package com.novafit.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.novafit.app.ui.screens.auth.LoginScreen
import com.novafit.app.ui.screens.home.HomeScreen
import com.novafit.app.ui.screens.miembros.MiembrosScreen
import com.novafit.app.ui.screens.planes.PlanesScreen
import com.novafit.app.ui.screens.membresias.MembresiasScreen
import com.novafit.app.ui.screens.pagos.PagosScreen
import com.novafit.app.ui.screens.entrenadores.EntrenadoresScreen
import com.novafit.app.ui.screens.clases.ClasesScreen
import com.novafit.app.ui.screens.asistencias.AsistenciasScreen

sealed class Pantalla(val ruta: String) {
    object Login : Pantalla("login")
    object Home : Pantalla("home")
    object Miembros : Pantalla("miembros")
    object Planes : Pantalla("planes")
    object Membresias : Pantalla("membresias")
    object Pagos : Pantalla("pagos")
    object Entrenadores : Pantalla("entrenadores")
    object Clases : Pantalla("clases")
    object Asistencias : Pantalla("asistencias")
}

@Composable
fun NovafitNavHost(navController: NavHostController, estaAutenticado: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (estaAutenticado) Pantalla.Home.ruta else Pantalla.Login.ruta
    ) {
        composable(Pantalla.Login.ruta) {
            LoginScreen(onLoginExitoso = {
                navController.navigate(Pantalla.Home.ruta) {
                    popUpTo(Pantalla.Login.ruta) { inclusive = true }
                }
            })
        }
        composable(Pantalla.Home.ruta) {
            HomeScreen(
                onNavegar = { ruta -> navController.navigate(ruta) },
                onCerrarSesion = {
                    navController.navigate(Pantalla.Login.ruta) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Pantalla.Miembros.ruta) {
            MiembrosScreen(onVolver = { navController.popBackStack() })
        }
        composable(Pantalla.Planes.ruta) {
            PlanesScreen(onVolver = { navController.popBackStack() })
        }
        composable(Pantalla.Membresias.ruta) {
            MembresiasScreen(onVolver = { navController.popBackStack() })
        }
        composable(Pantalla.Pagos.ruta) {
            PagosScreen(onVolver = { navController.popBackStack() })
        }
        composable(Pantalla.Entrenadores.ruta) {
            EntrenadoresScreen(onVolver = { navController.popBackStack() })
        }
        composable(Pantalla.Clases.ruta) {
            ClasesScreen(onVolver = { navController.popBackStack() })
        }
        composable(Pantalla.Asistencias.ruta) {
            AsistenciasScreen(onVolver = { navController.popBackStack() })
        }
    }
}
