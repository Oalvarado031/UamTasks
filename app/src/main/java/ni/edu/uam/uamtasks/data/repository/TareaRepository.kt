package ni.edu.uam.uamtasks.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.uamtasks.data.local.TareaDao
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.data.model.Tarea
import ni.edu.uam.uamtasks.data.model.TareaConMateria

/**
 * Repositorio para la entidad Tarea.
 */
class TareaRepository(private val dao: TareaDao) {

    val tareas: Flow<List<TareaConMateria>> = dao.obtenerTodasConMateria()

    val totalTareas: Flow<Int> = dao.contarTotal()

    fun contarPorEstado(estado: EstadoTarea): Flow<Int> = dao.contarPorEstado(estado)

    suspend fun obtenerPorId(id: Long): TareaConMateria? = dao.obtenerConMateriaPorId(id)

    suspend fun agregar(tarea: Tarea): Long = dao.insertar(tarea)

    suspend fun actualizar(tarea: Tarea) = dao.actualizar(tarea)

    suspend fun eliminar(tarea: Tarea) = dao.eliminar(tarea)
}
