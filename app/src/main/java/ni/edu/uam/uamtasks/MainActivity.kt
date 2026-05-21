package ni.edu.uam.uamtasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ni.edu.uam.uamtasks.ui.navigation.AppNavigation
import ni.edu.uam.uamtasks.ui.theme.UamTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UamTasksTheme {
                AppNavigation()
            }
        }
    }
}
