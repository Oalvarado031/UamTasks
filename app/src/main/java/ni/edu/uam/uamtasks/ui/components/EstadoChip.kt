package ni.edu.uam.uamtasks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.ui.theme.EstadoEnProgreso
import ni.edu.uam.uamtasks.ui.theme.EstadoEntregada
import ni.edu.uam.uamtasks.ui.theme.EstadoPendiente

@Composable
fun EstadoChip(
    estado: EstadoTarea,
    modifier: Modifier = Modifier
) {
    val color: Color = when (estado) {
        EstadoTarea.PENDIENTE -> EstadoPendiente
        EstadoTarea.EN_PROGRESO -> EstadoEnProgreso
        EstadoTarea.ENTREGADA -> EstadoEntregada
    }

    Text(
        text = estado.etiqueta,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = modifier
            .background(color, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
