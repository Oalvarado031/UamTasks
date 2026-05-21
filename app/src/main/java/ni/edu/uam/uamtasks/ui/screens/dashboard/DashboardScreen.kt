package ni.edu.uam.uamtasks.ui.screens.dashboard

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ni.edu.uam.uamtasks.ui.theme.EstadoEnProgreso
import ni.edu.uam.uamtasks.ui.theme.EstadoEntregada
import ni.edu.uam.uamtasks.ui.theme.EstadoPendiente
import ni.edu.uam.uamtasks.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    tareaViewModel: TareaViewModel,
    onIrATareas: () -> Unit,
    onAgregarTarea: () -> Unit
) {
    val dashboard by tareaViewModel.dashboard.collectAsStateWithLifecycle()

    // Memoizar callbacks para evitar crear lambdas nuevas en cada composición
    val handleIrATareas = remember { onIrATareas }
    val handleAgregarTarea = remember { onAgregarTarea }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("UAM Tasks", fontWeight = FontWeight.Bold)
                        Text(
                            "Tu gestor académico",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Tarjeta resumen total ---
            TotalCard(total = dashboard.total)

            // --- Conteo por estado ---
            Text(
                text = "Por estado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadoStatCard(
                    icon = Icons.Filled.PendingActions,
                    color = EstadoPendiente,
                    cantidad = dashboard.pendientes,
                    etiqueta = "Pendientes",
                    modifier = Modifier.weight(1f)
                )
                EstadoStatCard(
                    icon = Icons.Filled.HourglassEmpty,
                    color = EstadoEnProgreso,
                    cantidad = dashboard.enProgreso,
                    etiqueta = "En progreso",
                    modifier = Modifier.weight(1f)
                )
            }

            EstadoStatCard(
                icon = Icons.Filled.AssignmentTurnedIn,
                color = EstadoEntregada,
                cantidad = dashboard.entregadas,
                etiqueta = "Entregadas",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- Acciones ---
            Button(
                onClick = handleAgregarTarea,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text("Nueva tarea")
            }

            OutlinedButton(
                onClick = handleIrATareas,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Assignment, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text("Ver todas las tareas")
            }
        }
    }
}

@Composable
private fun TotalCard(total: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Assignment,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(
                    text = "Total de tareas",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.85f)
                )
                Text(
                    text = total.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun EstadoStatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    cantidad: Int,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = cantidad.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
