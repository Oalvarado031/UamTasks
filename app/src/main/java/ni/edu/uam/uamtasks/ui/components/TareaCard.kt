package ni.edu.uam.uamtasks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ni.edu.uam.uamtasks.data.model.TareaConMateria
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TareaCard(
    item: TareaConMateria,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formatoFecha = remember { SimpleDateFormat("dd/MM/yyyy", Locale("es", "NI")) }

    // CAMBIO 1: Uso de 'remember' asociado al valor del colorHex
    val colorMateria = remember(item.materia.colorHex) {
        parsearColor(item.materia.colorHex)
    }

    // CAMBIO 2: Uso del parámetro 'onClick' nativo de Card (Material 3)
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            // Barra de color de la materia
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(64.dp)
                    // CAMBIO 3: Importación directa de RoundedCornerShape
                    .background(colorMateria, shape = RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.tarea.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(colorMateria, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.materia.nombre,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EstadoChip(estado = item.tarea.estado)
                    PrioridadChip(prioridad = item.tarea.prioridad)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Fecha de entrega", // CAMBIO 4: Descripción de accesibilidad
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    // CAMBIO 5: Se memoriza el string final de la fecha
                    val fechaFormateada = remember(item.tarea.fechaEntrega) {
                        formatoFecha.format(Date(item.tarea.fechaEntrega))
                    }

                    Text(
                        text = "Entrega: $fechaFormateada",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

internal fun parsearColor(hex: String): Color =
    runCatching { Color(android.graphics.Color.parseColor(hex)) }
        .getOrDefault(Color(0xFF1A3A5C))