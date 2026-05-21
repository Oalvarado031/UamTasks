package ni.edu.uam.uamtasks

import android.app.Application
import ni.edu.uam.uamtasks.data.local.AppDatabase
import ni.edu.uam.uamtasks.data.repository.MateriaRepository
import ni.edu.uam.uamtasks.data.repository.TareaRepository

/**
 * Application principal: punto único de creación de la base de datos y los
 * repositorios. Se accede como contexto en `MainActivity` y luego se pasa al
 * `ViewModelFactory`.
 */
class UamTasksApp : Application() {

    val database: AppDatabase by lazy { AppDatabase.obtener(this) }
    val materiaRepository: MateriaRepository by lazy { MateriaRepository(database.materiaDao()) }
    val tareaRepository: TareaRepository by lazy { TareaRepository(database.tareaDao()) }
}
