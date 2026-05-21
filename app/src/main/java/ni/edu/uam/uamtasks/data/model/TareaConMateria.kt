package ni.edu.uam.uamtasks.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Vista combinada: Tarea con su Materia asociada.
 * Usada en la UI para mostrar el nombre de la materia sin hacer queries extra.
 */
data class TareaConMateria(
    @Embedded val tarea: Tarea,
    @Relation(
        parentColumn = "materiaId",
        entityColumn = "id"
    )
    val materia: Materia
)
