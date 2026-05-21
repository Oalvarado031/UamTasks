package ni.edu.uam.uamtasks.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Tarea académica vinculada a una Materia.
 *
 * Si se elimina una Materia, sus tareas también se eliminan (CASCADE).
 */
@Entity(
    tableName = "tareas",
    foreignKeys = [
        ForeignKey(
            entity = Materia::class,
            parentColumns = ["id"],
            childColumns = ["materiaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("materiaId")]
)
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String,
    val descripcion: String,
    /** Fecha de entrega como timestamp (milis epoch). */
    val fechaEntrega: Long,
    val prioridad: Prioridad,
    val estado: EstadoTarea,
    val materiaId: Long
)
