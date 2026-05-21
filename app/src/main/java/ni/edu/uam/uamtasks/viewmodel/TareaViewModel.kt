package ni.edu.uam.uamtasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.data.model.Prioridad
import ni.edu.uam.uamtasks.data.model.Tarea
import ni.edu.uam.uamtasks.data.model.TareaConMateria
import ni.edu.uam.uamtasks.data.repository.TareaRepository

/**
 * Estado del dashboard: conteos por estado.
 */
data class DashboardUiState(
    val total: Int = 0,
    val pendientes: Int = 0,
    val enProgreso: Int = 0,
    val entregadas: Int = 0
)

/**
 * Filtros aplicados a la lista de tareas.
 * `null` significa "sin filtro".
 */
data class FiltroTareas(
    val estado: EstadoTarea? = null,
    val materiaId: Long? = null
)

class TareaViewModel(
    private val repository: TareaRepository
) : ViewModel() {

    // Filtros expuestos como StateFlow para que la UI los modifique
    private val _filtro = MutableStateFlow(FiltroTareas())
    val filtro: StateFlow<FiltroTareas> = _filtro.asStateFlow()

    /**
     * Lista de tareas combinada con los filtros activos.
     * Cada vez que cambia el filtro o la BD, se recalcula automáticamente.
     */
    val tareasFiltradas: StateFlow<List<TareaConMateria>> = combine(
        repository.tareas,
        _filtro
    ) { lista, filtro ->
        lista.filter { item ->
            val pasaEstado = filtro.estado?.let { it == item.tarea.estado } ?: true
            val pasaMateria = filtro.materiaId?.let { it == item.tarea.materiaId } ?: true
            pasaEstado && pasaMateria
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    /**
     * Estado del dashboard: agrega conteos por estado en un solo objeto.
     */
    val dashboard: StateFlow<DashboardUiState> = combine(
        repository.totalTareas,
        repository.contarPorEstado(EstadoTarea.PENDIENTE),
        repository.contarPorEstado(EstadoTarea.EN_PROGRESO),
        repository.contarPorEstado(EstadoTarea.ENTREGADA)
    ) { total, pendientes, enProgreso, entregadas ->
        DashboardUiState(total, pendientes, enProgreso, entregadas)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState()
    )

    // ----- Acciones de filtro -----

    fun setFiltroEstado(estado: EstadoTarea?) {
        _filtro.value = _filtro.value.copy(estado = estado)
    }

    fun setFiltroMateria(materiaId: Long?) {
        _filtro.value = _filtro.value.copy(materiaId = materiaId)
    }

    fun limpiarFiltros() {
        _filtro.value = FiltroTareas()
    }

    // ----- CRUD -----

    fun agregar(
        titulo: String,
        descripcion: String,
        fechaEntrega: Long,
        prioridad: Prioridad,
        estado: EstadoTarea,
        materiaId: Long
    ) {
        viewModelScope.launch {
            repository.agregar(
                Tarea(
                    titulo = titulo.trim(),
                    descripcion = descripcion.trim(),
                    fechaEntrega = fechaEntrega,
                    prioridad = prioridad,
                    estado = estado,
                    materiaId = materiaId
                )
            )
        }
    }

    fun actualizar(tarea: Tarea) {
        viewModelScope.launch {
            repository.actualizar(tarea)
        }
    }

    fun marcarComoEntregada(tarea: Tarea) {
        viewModelScope.launch {
            repository.actualizar(tarea.copy(estado = EstadoTarea.ENTREGADA))
        }
    }

    fun eliminar(tarea: Tarea) {
        viewModelScope.launch {
            repository.eliminar(tarea)
        }
    }

    suspend fun obtenerPorId(id: Long): TareaConMateria? = repository.obtenerPorId(id)
}
