package ni.edu.uam.uamtasks.ui.screens.materias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ni.edu.uam.uamtasks.data.model.Materia
import ni.edu.uam.uamtasks.ui.components.EmptyState
import ni.edu.uam.uamtasks.ui.components.MateriaCard
import ni.edu.uam.uamtasks.viewmodel.MateriaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriasListScreen(
    materiaViewModel: MateriaViewModel,
    onAgregarMateria: () -> Unit,
    onEditarMateria: (Long) -> Unit
) {
    val materias by materiaViewModel.materias.collectAsStateWithLifecycle()
    var materiaAEliminar by remember { mutableStateOf<Materia?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Materias", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarMateria,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar materia",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        if (materias.isEmpty()) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                icon = Icons.Filled.School,
                titulo = "No hay materias",
                descripcion = "Agrega tu primera materia con el botón +"
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(
                    start = 16.dp, end = 16.dp, top = 12.dp, bottom = 80.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(materias, key = { it.id }) { materia ->
                    MateriaCard(
                        materia = materia,
                        onEditar = { onEditarMateria(materia.id) },
                        onEliminar = { materiaAEliminar = materia }
                    )
                }
            }
        }
    }

    materiaAEliminar?.let { materia ->
        AlertDialog(
            onDismissRequest = { materiaAEliminar = null },
            title = { Text("¿Eliminar materia?") },
            text = {
                Text(
                    "Se eliminará \"${materia.nombre}\" y todas sus tareas asociadas. " +
                            "Esta acción no se puede deshacer."
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    materiaViewModel.eliminar(materia)
                    materiaAEliminar = null
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { materiaAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
