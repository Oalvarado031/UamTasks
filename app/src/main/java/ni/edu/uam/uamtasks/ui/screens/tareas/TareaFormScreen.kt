package ni.edu.uam.uamtasks.ui.screens.tareas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.data.model.Materia
import ni.edu.uam.uamtasks.data.model.Prioridad
import ni.edu.uam.uamtasks.data.model.Tarea
import ni.edu.uam.uamtasks.viewmodel.MateriaViewModel
import ni.edu.uam.uamtasks.viewmodel.TareaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Formulario unificado para crear o editar una tarea.
 * Si `tareaId == null` o `0L` se crea una nueva; en caso contrario se precarga y se edita.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaFormScreen(
    tareaId: Long?,
    tareaViewModel: TareaViewModel,
    materiaViewModel: MateriaViewModel,
    onGuardado: () -> Unit,
    onCancelar: () -> Unit
) {
    val esEdicion = tareaId != null && tareaId > 0L
    val materias by materiaViewModel.materias.collectAsStateWithLifecycle()

    // Estado del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaEntrega by remember { mutableStateOf(System.currentTimeMillis()) }
    var prioridad by remember { mutableStateOf(Prioridad.MEDIA) }
    var estado by remember { mutableStateOf(EstadoTarea.PENDIENTE) }
    var materiaSeleccionada by remember { mutableStateOf<Materia?>(null) }

    // Errores de validación
    var errorTitulo by remember { mutableStateOf<String?>(null) }
    var errorMateria by remember { mutableStateOf<String?>(null) }

    var mostrarDatePicker by remember { mutableStateOf(false) }

    // Precarga al editar
    LaunchedEffect(tareaId) {
        if (esEdicion) {
            val item = tareaViewModel.obtenerPorId(tareaId!!)
            if (item != null) {
                titulo = item.tarea.titulo
                descripcion = item.tarea.descripcion
                fechaEntrega = item.tarea.fechaEntrega
                prioridad = item.tarea.prioridad
                estado = item.tarea.estado
                materiaSeleccionada = item.materia
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (esEdicion) "Editar tarea" else "Nueva tarea",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cancelar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Título
            OutlinedTextField(
                value = titulo,
                onValueChange = {
                    titulo = it
                    if (errorTitulo != null && it.isNotBlank()) errorTitulo = null
                },
                label = { Text("Título *") },
                isError = errorTitulo != null,
                supportingText = { errorTitulo?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Descripción
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                minLines = 3,
                maxLines = 6,
                modifier = Modifier.fillMaxWidth()
            )

            // Materia (dropdown)
            MateriaDropdown(
                materias = materias,
                seleccionada = materiaSeleccionada,
                onSelect = {
                    materiaSeleccionada = it
                    errorMateria = null
                },
                error = errorMateria
            )

            // Fecha de entrega
            FechaSelectorCard(
                fechaMs = fechaEntrega,
                onClick = { mostrarDatePicker = true }
            )

            // Prioridad
            EnumDropdown(
                etiqueta = "Prioridad",
                opciones = Prioridad.entries,
                seleccionada = prioridad,
                etiquetaDe = { it.etiqueta },
                onSelect = { prioridad = it }
            )

            // Estado
            EnumDropdown(
                etiqueta = "Estado",
                opciones = EstadoTarea.entries,
                seleccionada = estado,
                etiquetaDe = { it.etiqueta },
                onSelect = { estado = it }
            )

            Spacer(modifier = Modifier.size(8.dp))

            // Acciones
            Button(
                onClick = {
                    val tituloValido = titulo.isNotBlank().also {
                        if (!it) errorTitulo = "El título es obligatorio"
                    }
                    val materiaValida = (materiaSeleccionada != null).also {
                        if (!it) errorMateria = "Selecciona una materia"
                    }
                    if (tituloValido && materiaValida) {
                        if (esEdicion) {
                            tareaViewModel.actualizar(
                                Tarea(
                                    id = tareaId!!,
                                    titulo = titulo,
                                    descripcion = descripcion,
                                    fechaEntrega = fechaEntrega,
                                    prioridad = prioridad,
                                    estado = estado,
                                    materiaId = materiaSeleccionada!!.id
                                )
                            )
                        } else {
                            tareaViewModel.agregar(
                                titulo = titulo,
                                descripcion = descripcion,
                                fechaEntrega = fechaEntrega,
                                prioridad = prioridad,
                                estado = estado,
                                materiaId = materiaSeleccionada!!.id
                            )
                        }
                        onGuardado()
                    }
                },
                enabled = materias.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Save, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text(if (esEdicion) "Guardar cambios" else "Guardar tarea")
            }

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }

            if (materias.isEmpty()) {
                Text(
                    text = "No hay materias registradas. Crea una desde la pestaña Materias antes de agregar tareas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Diálogo de fecha
    if (mostrarDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = fechaEntrega)
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { fechaEntrega = it }
                    mostrarDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MateriaDropdown(
    materias: List<Materia>,
    seleccionada: Materia?,
    onSelect: (Materia) -> Unit,
    error: String?
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
    ) {
        OutlinedTextField(
            value = seleccionada?.let { "${it.codigo} · ${it.nombre}" } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Materia *") },
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
        )
        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            if (materias.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Sin materias disponibles") },
                    onClick = { expandido = false },
                    enabled = false
                )
            } else {
                materias.forEach { materia ->
                    DropdownMenuItem(
                        text = { Text("${materia.codigo} · ${materia.nombre}") },
                        onClick = {
                            onSelect(materia)
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> EnumDropdown(
    etiqueta: String,
    opciones: List<T>,
    seleccionada: T,
    etiquetaDe: (T) -> String,
    onSelect: (T) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
    ) {
        OutlinedTextField(
            value = etiquetaDe(seleccionada),
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
        )
        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            opciones.forEach { op ->
                DropdownMenuItem(
                    text = { Text(etiquetaDe(op)) },
                    onClick = {
                        onSelect(op)
                        expandido = false
                    }
                )
            }
        }
    }
}

/**
 * Card clickable que muestra la fecha seleccionada y dispara el DatePicker.
 * Más simple y limpio que envolver un OutlinedTextField disabled.
 */
@Composable
private fun FechaSelectorCard(
    fechaMs: Long,
    onClick: () -> Unit
) {
    val formato = remember { SimpleDateFormat("EEEE d 'de' MMMM, yyyy", Locale("es", "NI")) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Fecha de entrega",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formato.format(Date(fechaMs))
                        .replaceFirstChar { it.uppercase(Locale("es", "NI")) },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "Cambiar",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
