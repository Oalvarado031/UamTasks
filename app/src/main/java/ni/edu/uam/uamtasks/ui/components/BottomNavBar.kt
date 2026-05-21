package ni.edu.uam.uamtasks.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import ni.edu.uam.uamtasks.ui.navigation.Screen

@Composable
fun BottomNavBar(
    navController: NavController,
    rutaActual: String?
) {
    NavigationBar {
        Screen.tabs.forEach { tab ->
            NavigationBarItem(
                selected = rutaActual == tab.route,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}
