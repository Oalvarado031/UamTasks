package ni.edu.uam.uamtasks.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import ni.edu.uam.uamtasks.ui.components.BottomNavBar
import ni.edu.uam.uamtasks.ui.screens.dashboard.DashboardScreen
import ni.edu.uam.uamtasks.ui.screens.materias.MateriaFormScreen
import ni.edu.uam.uamtasks.ui.screens.materias.MateriasListScreen
import ni.edu.uam.uamtasks.ui.screens.tareas.TareaDetailScreen
import ni.edu.uam.uamtasks.ui.screens.tareas.TareaFormScreen
import ni.edu.uam.uamtasks.ui.screens.tareas.TareasListScreen
import ni.edu.uam.uamtasks.viewmodel.AppViewModelProvider
import ni.edu.uam.uamtasks.viewmodel.MateriaViewModel
import ni.edu.uam.uamtasks.viewmodel.TareaViewModel

/**
 * Componente raíz: configura el `Scaffold` con la Bottom Bar y el `NavHost`.
 *
 * Los ViewModels se obtienen aquí una vez y se pasan por parámetro a las
 * pantallas, garantizando que ambas pestañas comparten el mismo estado.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val tareaViewModel: TareaViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val materiaViewModel: MateriaViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    // Solo mostramos la Bottom Bar en las pestañas principales
    val mostrarBottomBar = Screen.tabs.any { it.route == rutaActual }

    Scaffold(
        bottomBar = {
            if (mostrarBottomBar) {
                BottomNavBar(navController = navController, rutaActual = rutaActual)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Tab.Dashboard.route,
            modifier = Modifier.padding(padding)
        ) {
            // --- Dashboard ---
            composable(Screen.Tab.Dashboard.route) {
                DashboardScreen(
                    tareaViewModel = tareaViewModel,
                    onIrATareas = {
                        navController.navigate(Screen.Tab.Tareas.route) {
                            launchSingleTop = true
                        }
                    },
                    onAgregarTarea = { navController.navigate(Screen.NuevaTarea.route) }
                )
            }

            // --- Lista de tareas ---
            composable(Screen.Tab.Tareas.route) {
                TareasListScreen(
                    tareaViewModel = tareaViewModel,
                    materiaViewModel = materiaViewModel,
                    onTareaClick = { id ->
                        navController.navigate(Screen.DetalleTarea.crearRuta(id))
                    },
                    onAgregarTarea = { navController.navigate(Screen.NuevaTarea.route) }
                )
            }

            // --- Detalle de tarea ---
            composable(
                route = Screen.DetalleTarea.route,
                arguments = listOf(navArgument("tareaId") { type = NavType.LongType })
            ) { entry ->
                val id = entry.arguments?.getLong("tareaId") ?: 0L
                TareaDetailScreen(
                    tareaId = id,
                    tareaViewModel = tareaViewModel,
                    onEditar = {
                        navController.navigate(Screen.EditarTarea.crearRuta(id))
                    },
                    onEliminado = { navController.popBackStack() },
                    onVolver = { navController.popBackStack() }
                )
            }

            // --- Formulario: nueva tarea ---
            composable(Screen.NuevaTarea.route) {
                TareaFormScreen(
                    tareaId = null,
                    tareaViewModel = tareaViewModel,
                    materiaViewModel = materiaViewModel,
                    onGuardado = { navController.popBackStack() },
                    onCancelar = { navController.popBackStack() }
                )
            }

            // --- Formulario: editar tarea ---
            composable(
                route = Screen.EditarTarea.route,
                arguments = listOf(navArgument("tareaId") { type = NavType.LongType })
            ) { entry ->
                val id = entry.arguments?.getLong("tareaId")
                TareaFormScreen(
                    tareaId = id,
                    tareaViewModel = tareaViewModel,
                    materiaViewModel = materiaViewModel,
                    onGuardado = { navController.popBackStack() },
                    onCancelar = { navController.popBackStack() }
                )
            }

            // --- Lista de materias ---
            composable(Screen.Tab.Materias.route) {
                MateriasListScreen(
                    materiaViewModel = materiaViewModel,
                    onAgregarMateria = { navController.navigate(Screen.NuevaMateria.route) },
                    onEditarMateria = { id ->
                        navController.navigate(Screen.EditarMateria.crearRuta(id))
                    }
                )
            }

            // --- Formulario: nueva materia ---
            composable(Screen.NuevaMateria.route) {
                MateriaFormScreen(
                    materiaId = null,
                    materiaViewModel = materiaViewModel,
                    onGuardado = { navController.popBackStack() },
                    onCancelar = { navController.popBackStack() }
                )
            }

            // --- Formulario: editar materia ---
            composable(
                route = Screen.EditarMateria.route,
                arguments = listOf(navArgument("materiaId") { type = NavType.LongType })
            ) { entry ->
                val id = entry.arguments?.getLong("materiaId")
                MateriaFormScreen(
                    materiaId = id,
                    materiaViewModel = materiaViewModel,
                    onGuardado = { navController.popBackStack() },
                    onCancelar = { navController.popBackStack() }
                )
            }
        }
    }
}
