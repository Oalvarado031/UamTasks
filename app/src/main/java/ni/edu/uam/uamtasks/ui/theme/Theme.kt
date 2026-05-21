package ni.edu.uam.uamtasks.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = UamPrimary,
    primaryContainer = UamPrimaryContainer,
    secondary = UamSecondary,
    tertiary = UamTertiary
)

private val DarkColorScheme = darkColorScheme(
    primary = UamPrimaryDark,
    secondary = UamSecondaryDark,
    tertiary = UamTertiaryDark
)

@Composable
fun UamTasksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Color dinámico activado en Android 12+ (puedes desactivarlo si quieres
    // que se respete siempre la paleta institucional UAM).
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
