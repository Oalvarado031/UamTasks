package ni.edu.uam.uamtasks.data.model

/**
 * Nivel de prioridad de una tarea académica.
 * Se persiste como String en Room (ver Converters).
 */
enum class Prioridad(val etiqueta: String) {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    companion object {
        fun fromEtiqueta(etiqueta: String): Prioridad =
            entries.firstOrNull { it.etiqueta == etiqueta } ?: MEDIA
    }
}
