package ni.edu.uam.uamtasks.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.uamtasks.data.local.MateriaDao
import ni.edu.uam.uamtasks.data.model.Materia

/**
 * Repositorio para la entidad Materia.
 * Encapsula el acceso al DAO y expone una API limpia al ViewModel.
 */
class MateriaRepository(private val dao: MateriaDao) {

    val materias: Flow<List<Materia>> = dao.obtenerTodas()

    suspend fun obtenerPorId(id: Long): Materia? = dao.obtenerPorId(id)

    suspend fun agregar(materia: Materia): Long = dao.insertar(materia)

    suspend fun actualizar(materia: Materia) = dao.actualizar(materia)

    suspend fun eliminar(materia: Materia) = dao.eliminar(materia)
}
