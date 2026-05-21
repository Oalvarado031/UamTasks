package ni.edu.uam.uamtasks.data.local

import androidx.room.TypeConverter
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.data.model.Prioridad

/**
 * Convierte enums a/desde String para que Room pueda almacenarlos en SQLite.
 */
class Converters {

    @TypeConverter
    fun fromPrioridad(value: Prioridad): String = value.name

    @TypeConverter
    fun toPrioridad(value: String): Prioridad =
        runCatching { Prioridad.valueOf(value) }.getOrDefault(Prioridad.MEDIA)

    @TypeConverter
    fun fromEstado(value: EstadoTarea): String = value.name

    @TypeConverter
    fun toEstado(value: String): EstadoTarea =
        runCatching { EstadoTarea.valueOf(value) }.getOrDefault(EstadoTarea.PENDIENTE)
}
