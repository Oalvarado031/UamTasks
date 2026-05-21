package ni.edu.uam.uamtasks.ui.utils

import androidx.compose.runtime.Stable
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Object singleton para formatter de fechas seguro y optimizado.
 * Se crea una sola vez y se reutiliza para evitar crear nuevos SimpleDateFormat en cada composición.
 *
 * Formatos soportados:
 * - SHORT: "dd/MM/yyyy" (01/05/2026)
 * - LONG: "EEEE d 'de' MMMM, yyyy" (Wednesday 1 de May, 2026)
 */
@Stable
object DateFormatterUtil {
    private val formatterShort = SimpleDateFormat("dd/MM/yyyy", Locale("es", "NI"))
    private val formatterLong = SimpleDateFormat("EEEE d 'de' MMMM, yyyy", Locale("es", "NI"))

    fun format(timeMillis: Long, style: FormatStyle = FormatStyle.SHORT): String {
        val formatter = when (style) {
            FormatStyle.SHORT -> formatterShort
            FormatStyle.LONG -> formatterLong
        }
        return formatter.format(java.util.Date(timeMillis))
    }
}

enum class FormatStyle {
    SHORT,  // "dd/MM/yyyy"
    LONG    // "EEEE d 'de' MMMM, yyyy"
}

/**
 * Anotación para marcar data classes como estables en Compose.
 * Las clases estables no fuerzan recomposiciones cuando sus parámetros cambian.
 *
 * Uso en data classes importantes para la UI:
 * @Stable
 * data class MyData(...)
 */
@Target(AnnotationTarget.CLASS)
annotation class ComposeStable


