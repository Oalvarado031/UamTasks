package ni.edu.uam.uamtasks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Materia universitaria (Ej. "Programación Móvil", "Base de Datos").
 * Cada Tarea pertenece a una Materia.
 */
@Entity(tableName = "materias")
data class Materia(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val docente: String,
    val codigo: String,
    /** Color en formato hex "#RRGGBB" para identificar visualmente la materia. */
    val colorHex: String = "#1A3A5C"
)
