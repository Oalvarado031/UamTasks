package ni.edu.uam.uamtasks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.uamtasks.data.model.Materia

@Composable
fun MateriaCard(
    materia: Materia,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = parsearColor(materia.colorHex)
    val iniciales = obtenerIniciales(materia.nombre)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEditar() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {

            // Barra vertical de color a la izquierda
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(color)
            )

            // Contenido principal
            Row(
                modifier = Modifier
                    .padding(start = 14.dp, top = 14.dp, end = 6.dp, bottom = 14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar con degradado
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(color, color.copy(alpha = 0.65f))
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = iniciales,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Texto
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = materia.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Chip pequeño con el código
                        Box(
                            modifier = Modifier
                                .background(
                                    color = color.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = materia.codigo,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = color,
                                fontSize = 11.sp
                            )
                        }
                        if (materia.docente.isNotBlank()) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = materia.docente,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }
                }

                // Acciones en círculos sutiles
                AccionCircular(
                    icono = Icons.Filled.Edit,
                    descripcion = "Editar materia",
                    tintColor = MaterialTheme.colorScheme.primary,
                    onClick = onEditar
                )
                AccionCircular(
                    icono = Icons.Filled.Delete,
                    descripcion = "Eliminar materia",
                    tintColor = MaterialTheme.colorScheme.error,
                    onClick = onEliminar
                )
            }
        }
    }
}

@Composable
private fun AccionCircular(
    icono: ImageVector,
    descripcion: String,
    tintColor: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color = tintColor.copy(alpha = 0.1f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = descripcion,
                tint = tintColor,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Iniciales del nombre de la materia (ignorando preposiciones).
 *  "Base de Datos"           -> "BD"
 *  "Programación Móvil"      -> "PM"
 *  "Ingeniería de Software"  -> "IS"
 *  "Cálculo"                 -> "CA"
 */
internal fun obtenerIniciales(nombre: String): String {
    val palabras = nombre.trim().split(" ")
        .filter { it.isNotBlank() }
        .filter { it.lowercase() !in palabrasIgnoradas }

    return when {
        palabras.size >= 2 -> "${palabras[0].first()}${palabras[1].first()}".uppercase()
        palabras.size == 1 -> palabras[0].take(2).uppercase()
        else -> "??"
    }
}

private val palabrasIgnoradas = setOf("de", "del", "la", "el", "los", "las", "y", "e", "a")