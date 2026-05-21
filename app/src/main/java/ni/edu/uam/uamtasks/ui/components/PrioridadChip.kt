package ni.edu.uam.uamtasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ni.edu.uam.uamtasks.data.model.Prioridad
import ni.edu.uam.uamtasks.ui.theme.PrioridadAlta
import ni.edu.uam.uamtasks.ui.theme.PrioridadBaja
import ni.edu.uam.uamtasks.ui.theme.PrioridadMedia

@Composable
fun PrioridadChip(
    prioridad: Prioridad,
    modifier: Modifier = Modifier
) {
    val color: Color = when (prioridad) {
        Prioridad.ALTA -> PrioridadAlta
        Prioridad.MEDIA -> PrioridadMedia
        Prioridad.BAJA -> PrioridadBaja
    }

    Text(
        text = prioridad.etiqueta,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(50))
            .border(BorderStroke(1.dp, color), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
