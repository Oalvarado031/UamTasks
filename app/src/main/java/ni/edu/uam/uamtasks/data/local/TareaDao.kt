package ni.edu.uam.uamtasks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.data.model.Tarea
import ni.edu.uam.uamtasks.data.model.TareaConMateria

@Dao
interface TareaDao {

    @Transaction
    @Query("SELECT * FROM tareas ORDER BY fechaEntrega ASC")
    fun obtenerTodasConMateria(): Flow<List<TareaConMateria>>

    @Transaction
    @Query("SELECT * FROM tareas WHERE id = :id LIMIT 1")
    suspend fun obtenerConMateriaPorId(id: Long): TareaConMateria?

    @Query("SELECT COUNT(*) FROM tareas WHERE estado = :estado")
    fun contarPorEstado(estado: EstadoTarea): Flow<Int>

    @Query("SELECT COUNT(*) FROM tareas")
    fun contarTotal(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(tarea: Tarea): Long

    @Update
    suspend fun actualizar(tarea: Tarea)

    @Delete
    suspend fun eliminar(tarea: Tarea)
}
