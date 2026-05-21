package ni.edu.uam.uamtasks.ui.screens.materias

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.uamtasks.data.model.Materia
import ni.edu.uam.uamtasks.ui.components.parsearColor
import ni.edu.uam.uamtasks.viewmodel.MateriaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriaFormScreen(
    materiaId: Long?,
    materiaViewModel: MateriaViewModel,
    onGuardado: () -> Unit,
    onCancelar: () -> Unit
) {
    val esEdicion = materiaId != null && materiaId > 0L

    var nombre by remember { mutableStateOf("") }
    var docente by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var colorHex by remember { mutableStateOf(coloresDisponibles.first()) }

    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorCodigo by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(materiaId) {
        if (esEdicion) {
            val m = materiaViewModel.obtenerPorId(materiaId!!)
            if (m != null) {
                nombre = m.nombre
                docente = m.docente
                codigo = m.codigo
                colorHex = m.colorHex
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (esEdicion) "Editar materia" else "Nueva materia",
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
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    if (errorNombre != null && it.isNotBlank()) errorNombre = null
                },
                label = { Text("Nombre de la materia *") },
                isError = errorNombre != null,
                supportingText = { errorNombre?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = codigo,
                onValueChange = {
                    codigo = it
                    if (errorCodigo != null && it.isNotBlank()) errorCodigo = null
                },
                label = { Text("Código *") },
                placeholder = { Text("Ej. ISW-501") },
                isError = errorCodigo != null,
                supportingText = { errorCodigo?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = docente,
                onValueChange = { docente = it },
                label = { Text("Docente") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Color identificador",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            SelectorColor(
                colorActual = colorHex,
                onSeleccionar = { colorHex = it }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = {
                    val nombreValido = nombre.isNotBlank().also {
                        if (!it) errorNombre = "El nombre es obligatorio"
                    }
                    val codigoValido = codigo.isNotBlank().also {
                        if (!it) errorCodigo = "El código es obligatorio"
                    }
                    if (nombreValido && codigoValido) {
                        if (esEdicion) {
                            materiaViewModel.actualizar(
                                Materia(
                                    id = materiaId!!,
                                    nombre = nombre.trim(),
                                    docente = docente.trim(),
                                    codigo = codigo.trim(),
                                    colorHex = colorHex
                                )
                            )
                        } else {
                            materiaViewModel.agregar(nombre, docente, codigo, colorHex)
                        }
                        onGuardado()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Save, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text(if (esEdicion) "Guardar cambios" else "Guardar materia")
            }

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}

private val coloresDisponibles = listOf(
    "#1A3A5C", // Azul UAM
    "#4CAF50", // Verde
    "#FF7043", // Naranja
    "#7E57C2", // Morado
    "#26A69A", // Teal
    "#EC407A", // Rosa
    "#FFB300", // Ámbar
    "#5C6BC0"  // Índigo
)

@Composable
private fun SelectorColor(
    colorActual: String,
    onSeleccionar: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        coloresDisponibles.forEach { hex ->
            val seleccionado = hex == colorActual
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(parsearColor(hex), CircleShape)
                    .border(
                        width = if (seleccionado) 3.dp else 0.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape
                    )
                    .clickable { onSeleccionar(hex) },
                contentAlignment = Alignment.Center
            ) {
                if (seleccionado) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}
