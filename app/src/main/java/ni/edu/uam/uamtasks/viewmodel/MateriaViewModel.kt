package ni.edu.uam.uamtasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.uamtasks.data.model.Materia
import ni.edu.uam.uamtasks.data.repository.MateriaRepository

/**
 * ViewModel encargado del CRUD de Materias.
 * Expone la lista actual como un StateFlow que la UI observa.
 */
class MateriaViewModel(
    private val repository: MateriaRepository
) : ViewModel() {

    val materias: StateFlow<List<Materia>> = repository.materias
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun agregar(nombre: String, docente: String, codigo: String, colorHex: String) {
        viewModelScope.launch {
            repository.agregar(
                Materia(
                    nombre = nombre.trim(),
                    docente = docente.trim(),
                    codigo = codigo.trim(),
                    colorHex = colorHex
                )
            )
        }
    }

    fun actualizar(materia: Materia) {
        viewModelScope.launch {
            repository.actualizar(materia)
        }
    }

    fun eliminar(materia: Materia) {
        viewModelScope.launch {
            repository.eliminar(materia)
        }
    }

    suspend fun obtenerPorId(id: Long): Materia? = repository.obtenerPorId(id)
}
