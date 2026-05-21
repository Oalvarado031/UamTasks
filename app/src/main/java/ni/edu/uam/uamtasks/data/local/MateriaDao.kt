package ni.edu.uam.uamtasks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.uamtasks.data.model.Materia

@Dao
interface MateriaDao {

    @Query("SELECT * FROM materias ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<Materia>>

    @Query("SELECT * FROM materias WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): Materia?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(materia: Materia): Long

    @Update
    suspend fun actualizar(materia: Materia)

    @Delete
    suspend fun eliminar(materia: Materia)
}
