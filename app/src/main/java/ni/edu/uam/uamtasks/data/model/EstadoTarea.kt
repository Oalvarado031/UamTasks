package ni.edu.uam.uamtasks.data.model

/**
 * Estado de avance de una tarea académica.
 */
enum class EstadoTarea(val etiqueta: String) {
    PENDIENTE("Pendiente"),
    EN_PROGRESO("En progreso"),
    ENTREGADA("Entregada");

    companion object {
        fun fromEtiqueta(etiqueta: String): EstadoTarea =
            entries.firstOrNull { it.etiqueta == etiqueta } ?: PENDIENTE
    }
}
