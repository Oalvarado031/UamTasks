package ni.edu.uam.uamtasks.ui.screens.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.uamtasks.data.model.TareaConMateria
import ni.edu.uam.uamtasks.ui.components.EstadoChip
import ni.edu.uam.uamtasks.ui.components.PrioridadChip
import ni.edu.uam.uamtasks.ui.components.parsearColor
import ni.edu.uam.uamtasks.ui.utils.DateFormatterUtil
import ni.edu.uam.uamtasks.ui.utils.FormatStyle
import ni.edu.uam.uamtasks.viewmodel.TareaViewModel
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaDetailScreen(
    tareaId: Long,
    tareaViewModel: TareaViewModel,
    onEditar: () -> Unit,
    onEliminado: () -> Unit,
    onVolver: () -> Unit
) {
    var item by remember { mutableStateOf<TareaConMateria?>(null) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }

    LaunchedEffect(tareaId) {
        item = tareaViewModel.obtenerPorId(tareaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de tarea", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditar) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar"
                        )
                    }
                    IconButton(onClick = { mostrarDialogoEliminar = true }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Eliminar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        val tareaConMateria = item
        if (tareaConMateria == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            DetalleContenido(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                item = tareaConMateria,
                onEditar = onEditar,
                onSolicitarEliminar = { mostrarDialogoEliminar = true }
            )
        }
    }

    if (mostrarDialogoEliminar && item != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("¿Eliminar tarea?") },
            text = {
                Text("Esta acción no se puede deshacer. ¿Deseas eliminar \"${item!!.tarea.titulo}\"?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        tareaViewModel.eliminar(item!!.tarea)
                        mostrarDialogoEliminar = false
                        onEliminado()
                    }
                ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun DetalleContenido(
    modifier: Modifier = Modifier,
    item: TareaConMateria,
    onEditar: () -> Unit,
    onSolicitarEliminar: () -> Unit
) {
    val colorMateria = remember(item.materia.colorHex) { parsearColor(item.materia.colorHex) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Materia
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(colorMateria, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = item.materia.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${item.materia.codigo} · ${item.materia.docente}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Título
        Text(
            text = item.tarea.titulo,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        // Chips de estado y prioridad
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EstadoChip(estado = item.tarea.estado)
            PrioridadChip(prioridad = item.tarea.prioridad)
        }

        HorizontalDivider()

        // Fecha de entrega
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(
                    text = "Fecha de entrega",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = DateFormatterUtil.format(item.tarea.fechaEntrega, FormatStyle.LONG)
                        .replaceFirstChar { it.uppercase(Locale("es", "NI")) },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        HorizontalDivider()

        // Descripción
        Text(
            text = "Descripción",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = item.tarea.descripcion.ifBlank { "Sin descripción" },
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Acciones
        Button(
            onClick = onEditar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Edit, contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text("Editar tarea")
        }

        OutlinedButton(
            onClick = onSolicitarEliminar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(Icons.Filled.Delete, contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text("Eliminar tarea")
        }
    }
}
