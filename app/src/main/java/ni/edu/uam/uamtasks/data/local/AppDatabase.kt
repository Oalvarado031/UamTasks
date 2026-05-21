package ni.edu.uam.uamtasks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ni.edu.uam.uamtasks.data.model.EstadoTarea
import ni.edu.uam.uamtasks.data.model.Materia
import ni.edu.uam.uamtasks.data.model.Prioridad
import ni.edu.uam.uamtasks.data.model.Tarea

/**
 * Base de datos Room de la aplicación.
 *
 * Contiene las tablas `materias` y `tareas`.
 * Se inicializa una sola vez (singleton) y se siembra con datos de ejemplo
 * la primera vez que se crea.
 */
@Database(
    entities = [Materia::class, Tarea::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun materiaDao(): MateriaDao
    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtener(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "uamtasks.db"
                )
                    .addCallback(SeedCallback())
                    .build()
                INSTANCE = instancia
                instancia
            }
        }

        /** Inserta datos iniciales para que la app se vea poblada al primer arranque. */
        private class SeedCallback : Callback() {
            private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch { sembrar(database) }
                }
            }

            private suspend fun sembrar(db: AppDatabase) {
                val materiaDao = db.materiaDao()
                val tareaDao = db.tareaDao()

                val idProg = materiaDao.insertar(
                    Materia(
                        nombre = "Programación Móvil",
                        docente = "Ing. Pérez",
                        codigo = "ISW-501",
                        colorHex = "#1A3A5C"
                    )
                )
                val idBd = materiaDao.insertar(
                    Materia(
                        nombre = "Base de Datos",
                        docente = "Ing. Hernández",
                        codigo = "ISW-402",
                        colorHex = "#4CAF50"
                    )
                )
                val idIng = materiaDao.insertar(
                    Materia(
                        nombre = "Ingeniería de Software",
                        docente = "Ing. López",
                        codigo = "ISW-301",
                        colorHex = "#FF7043"
                    )
                )

                val ahora = System.currentTimeMillis()
                val dia = 24L * 60 * 60 * 1000

                tareaDao.insertar(
                    Tarea(
                        titulo = "Proyecto final UAM Tasks",
                        descripcion = "Entregar app en Android Studio con CRUD completo",
                        fechaEntrega = ahora + (7 * dia),
                        prioridad = Prioridad.ALTA,
                        estado = EstadoTarea.EN_PROGRESO,
                        materiaId = idProg
                    )
                )
                tareaDao.insertar(
                    Tarea(
                        titulo = "Modelo entidad-relación",
                        descripcion = "Diseñar MER para el sistema de biblioteca",
                        fechaEntrega = ahora + (3 * dia),
                        prioridad = Prioridad.MEDIA,
                        estado = EstadoTarea.PENDIENTE,
                        materiaId = idBd
                    )
                )
                tareaDao.insertar(
                    Tarea(
                        titulo = "Documento de requisitos",
                        descripcion = "SRS según IEEE 830 para el caso de estudio",
                        fechaEntrega = ahora + (10 * dia),
                        prioridad = Prioridad.BAJA,
                        estado = EstadoTarea.PENDIENTE,
                        materiaId = idIng
                    )
                )
                tareaDao.insertar(
                    Tarea(
                        titulo = "Quiz Jetpack Compose",
                        descripcion = "Estudiar State, Recomposición y Navigation",
                        fechaEntrega = ahora + (1 * dia),
                        prioridad = Prioridad.ALTA,
                        estado = EstadoTarea.PENDIENTE,
                        materiaId = idProg
                    )
                )
            }
        }
    }
}
