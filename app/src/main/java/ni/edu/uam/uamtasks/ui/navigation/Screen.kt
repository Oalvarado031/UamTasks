package ni.edu.uam.uamtasks.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Rutas tipadas de toda la app. Centralizar evita errores de strings sueltos.
 */
sealed class Screen(val route: String) {

    // --- Pestañas principales (con icono en Bottom Bar) ---
    sealed class Tab(route: String, val label: String, val icon: ImageVector) : Screen(route) {
        data object Dashboard : Tab("dashboard", "Inicio", Icons.Filled.Dashboard)
        data object Tareas : Tab("tareas", "Tareas", Icons.AutoMirrored.Filled.Assignment)
        data object Materias : Tab("materias", "Materias", Icons.Filled.School)
    }

    // --- Pantallas secundarias ---
    data object NuevaTarea : Screen("tareas/nueva")
    data object EditarTarea : Screen("tareas/editar/{tareaId}") {
        fun crearRuta(tareaId: Long) = "tareas/editar/$tareaId"
    }
    data object DetalleTarea : Screen("tareas/detalle/{tareaId}") {
        fun crearRuta(tareaId: Long) = "tareas/detalle/$tareaId"
    }
    data object NuevaMateria : Screen("materias/nueva")
    data object EditarMateria : Screen("materias/editar/{materiaId}") {
        fun crearRuta(materiaId: Long) = "materias/editar/$materiaId"
    }

    companion object {
        val tabs = listOf(Tab.Dashboard, Tab.Tareas, Tab.Materias)
    }
}
