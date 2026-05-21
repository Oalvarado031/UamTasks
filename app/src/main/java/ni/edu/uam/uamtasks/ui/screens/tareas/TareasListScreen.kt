package ni.edu.uam.uamtasks.ui.screens.tareas

<<<<<<< HEAD
=======
import androidx.compose.foundation.Image
>>>>>>> diedereich
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
<<<<<<< HEAD
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
=======
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
>>>>>>> diedereich
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ni.edu.uam.uamtasks.R
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.ui.components.EmptyState
import ni.edu.uam.uamtasks.ui.components.TareaCard
import ni.edu.uam.uamtasks.viewmodel.MateriaViewModel
import ni.edu.uam.uamtasks.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasListScreen(
    tareaViewModel: TareaViewModel,
    materiaViewModel: MateriaViewModel,
    onTareaClick: (Long) -> Unit,
    onAgregarTarea: () -> Unit
) {
    val tareas by tareaViewModel.tareasFiltradas.collectAsStateWithLifecycle()
    val filtro by tareaViewModel.filtro.collectAsStateWithLifecycle()
    val materias by materiaViewModel.materias.collectAsStateWithLifecycle()

    // Memoizar callbacks
    val handleTareaClick = remember { onTareaClick }
    val handleAgregarTarea = remember { onAgregarTarea }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.uam_logo),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Tareas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = handleAgregarTarea,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar tarea",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ----- Filtros por estado -----
            Text(
                text = "Filtrar por estado",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filtro.estado == null,
                    onClick = { tareaViewModel.setFiltroEstado(null) },
                    label = { Text("Todas") }
                )
                EstadoTarea.entries.forEach { estado ->
                    FilterChip(
                        selected = filtro.estado == estado,
                        onClick = { tareaViewModel.setFiltroEstado(estado) },
                        label = { Text(estado.etiqueta) }
                    )
                }
            }

            // ----- Filtros por materia -----
            if (materias.isNotEmpty()) {
                Text(
                    text = "Filtrar por materia",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = filtro.materiaId == null,
                        onClick = { tareaViewModel.setFiltroMateria(null) },
                        label = { Text("Todas") }
                    )
                    materias.forEach { materia ->
                        FilterChip(
                            selected = filtro.materiaId == materia.id,
                            onClick = { tareaViewModel.setFiltroMateria(materia.id) },
                            label = { Text(materia.codigo) }
                        )
                    }
                }
            }

            // ----- Botón "limpiar filtros" cuando hay alguno activo -----
            if (filtro.estado != null || filtro.materiaId != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilterChip(
                        selected = false,
                        onClick = { tareaViewModel.limpiarFiltros() },
                        leadingIcon = { Icon(Icons.Filled.Close, contentDescription = null) },
                        label = { Text("Limpiar filtros") }
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            // ----- Lista con soporte de Swipe to Delete -----
            if (tareas.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.Assignment,
                    titulo = "No hay tareas",
                    descripcion = "Agrega tu primera tarea con el botón +"
                )
            } else {
                LazyColumn(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        start = 16.dp, end = 16.dp, top = 4.dp, bottom = 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tareas, key = { it.tarea.id }) { item ->
<<<<<<< HEAD
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { dismissValue ->
                                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                    tareaViewModel.eliminar(item.tarea)
                                    true
                                } else {
                                    false
                                }
                            }
=======
                        TareaCard(
                            item = item,
                            onClick = { handleTareaClick(item.tarea.id) },
                            onMarcarEntregada = { tareaViewModel.marcarComoEntregada(item.tarea) }
>>>>>>> diedereich
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {
                                val color = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color, shape = MaterialTheme.shapes.medium)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Eliminar tarea",
                                        tint = Color.White
                                    )
                                }
                            }
                        ) {
                            TareaCard(
                                item = item,
                                onClick = { onTareaClick(item.tarea.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}