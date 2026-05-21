package ni.edu.uam.uamtasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ni.edu.uam.uamtasks.UamTasksApp

/**
 * Factory centralizado para todos los ViewModels.
 * Resuelve la `Application` desde los `CreationExtras` y obtiene los
 * repositorios desde ahí, sin depender de un framework de DI externo.
 */
object AppViewModelProvider {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            TareaViewModel(uamApp().tareaRepository)
        }
        initializer {
            MateriaViewModel(uamApp().materiaRepository)
        }
    }

    private fun CreationExtras.uamApp(): UamTasksApp =
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as UamTasksApp
}
