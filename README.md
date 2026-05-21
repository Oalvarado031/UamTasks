# UAM Tasks 📚

Aplicación Android para la gestión de tareas académicas universitarias.
Proyecto de la **Universidad Americana (UAM) - Nicaragua**.

![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?logo=jetpackcompose)
![Room](https://img.shields.io/badge/Room-2.6.1-3DDC84?logo=android)
![minSdk](https://img.shields.io/badge/minSdk-24-blue)

---

## 📋 Descripción

UAM Tasks permite a estudiantes universitarios organizar sus **tareas** y
**materias** del semestre. Cada tarea está vinculada a una materia, tiene una
fecha de entrega, una prioridad y un estado de avance.

La aplicación implementa el patrón **MVVM** (Model-View-ViewModel) con
Jetpack Compose y persiste los datos localmente con **Room**.

## ✨ Funcionalidades

- **Dashboard** con conteo total de tareas y desglose por estado.
- **CRUD completo de Tareas**: agregar, listar, ver detalle, editar y eliminar.
- **CRUD completo de Materias**: agregar, listar, editar y eliminar.
- **Filtros** por estado (Pendiente / En progreso / Entregada) y por materia.
- **Validaciones** en formularios (campos obligatorios marcados con `*`).
- **DatePicker** nativo Material 3 para elegir la fecha de entrega.
- **Selector de color** institucional para cada materia.
- **Persistencia local** con Room: los datos sobreviven al cerrar la app.
- **Datos de ejemplo** sembrados automáticamente en el primer arranque.
- **Tema Material 3** con paleta institucional UAM y soporte modo oscuro.

## 🚀 Cómo abrir el proyecto

1. Abre **Android Studio** (versión Ladybug 2024.2.1 o superior recomendada).
2. `File > Open...` y selecciona la carpeta `UamTasks`.
3. Espera a que Gradle sincronice (descargará Compose BOM, Room, Navigation y KSP).
4. Crea un emulador con **API 24 o superior** (o conecta un dispositivo físico).
5. Pulsa **Run ▶** (`Shift+F10`).

> Si Gradle te pide aceptar licencias del SDK, ejecuta:
> `~/Android/Sdk/cmdline-tools/latest/bin/sdkmanager --licenses`

## 🏗️ Arquitectura

```
ni.edu.uam.uamtasks/
├── MainActivity.kt              ← Entry point (setContent + Tema)
├── UamTasksApp.kt               ← Application class (inicializa Room)
├── data/
│   ├── model/                   ← Entidades + Enums + Relaciones
│   │   ├── Materia.kt
│   │   ├── Tarea.kt
│   │   ├── Prioridad.kt
│   │   ├── EstadoTarea.kt
│   │   └── TareaConMateria.kt
│   ├── local/                   ← Room: DAOs, DB, Converters
│   │   ├── AppDatabase.kt
│   │   ├── MateriaDao.kt
│   │   ├── TareaDao.kt
│   │   └── Converters.kt
│   └── repository/              ← Abstracción sobre los DAOs
│       ├── MateriaRepository.kt
│       └── TareaRepository.kt
├── viewmodel/
│   ├── TareaViewModel.kt        ← Estado de tareas + filtros + dashboard
│   ├── MateriaViewModel.kt      ← Estado de materias
│   └── ViewModelFactory.kt      ← Inyección de dependencias manual
└── ui/
    ├── theme/                   ← Color, Type, Theme (paleta UAM)
    ├── navigation/              ← Screen.kt (rutas) + AppNavigation.kt
    ├── components/              ← Componentes reutilizables
    │   ├── BottomNavBar.kt
    │   ├── TareaCard.kt
    │   ├── MateriaCard.kt
    │   ├── EstadoChip.kt
    │   ├── PrioridadChip.kt
    │   └── EmptyState.kt
    └── screens/
        ├── dashboard/DashboardScreen.kt
        ├── tareas/TareasListScreen.kt
        ├── tareas/TareaDetailScreen.kt
        ├── tareas/TareaFormScreen.kt
        ├── materias/MateriasListScreen.kt
        └── materias/MateriaFormScreen.kt
```

## 🛠️ Tecnologías

| Categoría | Tecnología | Versión |
|---|---|---|
| Lenguaje | Kotlin | 2.2.10 |
| UI | Jetpack Compose + Material 3 | BOM 2026.02.01 |
| Navegación | Navigation Compose | 2.8.5 |
| Persistencia | Room | 2.6.1 |
| Async | Kotlin Coroutines + Flow | — |
| DI | Factory manual con `viewModelFactory` | — |
| Build | Android Gradle Plugin | 9.2.1 |
| Procesador anotaciones | KSP | 2.2.10-2.0.2 |
| minSdk / targetSdk | 24 / 36 | — |

## 📱 Pantallas

1. **Dashboard** — Resumen visual con cards de conteo por estado.
2. **Lista de Tareas** — Lista filtrable con chips de estado y materia.
3. **Detalle de Tarea** — Vista completa con botones de editar/eliminar.
4. **Formulario de Tarea** — Crear/editar con DatePicker y dropdowns.
5. **Lista de Materias** — CRUD de materias con color identificador.
6. **Formulario de Materia** — Crear/editar con selector de color.

Navegación inferior (Bottom Bar) entre **Inicio**, **Tareas** y **Materias**.

## 👥 Autores

Proyecto académico - Universidad Americana (UAM) - Managua, Nicaragua.
