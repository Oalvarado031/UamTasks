# Guía de commits — UAM Tasks

Para el criterio **"Uso de Git y GitHub (10 pts)"** se evalúa que los commits
usen **Conventional Commits** (`feat:`, `fix:`, `docs:`, `style:`, etc.) y
que el repositorio muestre un avance progresivo del proyecto.

## Convención

```
<tipo>: <descripción corta en imperativo, minúsculas>

[cuerpo opcional explicando el qué y el por qué]
```

| Tipo | Cuándo usarlo |
|---|---|
| `feat:` | Nueva funcionalidad para el usuario |
| `fix:` | Corrección de un bug |
| `docs:` | Cambios solo en documentación |
| `style:` | Formato, sin cambios de lógica |
| `refactor:` | Reorganización de código sin cambiar comportamiento |
| `chore:` | Tareas de mantenimiento (gradle, dependencias) |

## Secuencia recomendada de commits

Si vas a subir el proyecto desde cero, esta secuencia se ve **natural**
(no parece "un solo commit gigante"):

```bash
# 1. Setup inicial
git init
git add .gitignore README.md
git commit -m "chore: initial commit with .gitignore"

git add settings.gradle.kts build.gradle.kts gradle/ gradlew gradlew.bat gradle.properties
git commit -m "chore: add gradle wrapper and project config"

git add app/build.gradle.kts app/proguard-rules.pro app/.gitignore
git commit -m "chore: configure app module with compose, room and navigation"

# 2. Recursos base
git add app/src/main/AndroidManifest.xml app/src/main/res/
git commit -m "feat: add android manifest, resources and launcher icon"

# 3. Tema
git add app/src/main/java/ni/edu/uam/uamtasks/ui/theme/
git commit -m "feat: add material 3 theme with uam institutional colors"

# 4. Modelos de datos
git add app/src/main/java/ni/edu/uam/uamtasks/data/model/
git commit -m "feat: add Materia and Tarea entities with Prioridad and EstadoTarea enums"

# 5. Capa de persistencia
git add app/src/main/java/ni/edu/uam/uamtasks/data/local/
git commit -m "feat: add Room database with DAOs and seed data"

# 6. Repositorios
git add app/src/main/java/ni/edu/uam/uamtasks/data/repository/
git commit -m "feat: add repositories to abstract data access"

# 7. ViewModels
git add app/src/main/java/ni/edu/uam/uamtasks/viewmodel/
git commit -m "feat: add ViewModels with StateFlow and combined filters"

# 8. Application + MainActivity
git add app/src/main/java/ni/edu/uam/uamtasks/UamTasksApp.kt app/src/main/java/ni/edu/uam/uamtasks/MainActivity.kt
git commit -m "feat: add Application class and MainActivity entry point"

# 9. Navegación
git add app/src/main/java/ni/edu/uam/uamtasks/ui/navigation/
git commit -m "feat: add Navigation Compose with typed routes and bottom bar"

# 10. Componentes reutilizables
git add app/src/main/java/ni/edu/uam/uamtasks/ui/components/
git commit -m "feat: add reusable UI components (cards, chips, empty state)"

# 11. Dashboard
git add app/src/main/java/ni/edu/uam/uamtasks/ui/screens/dashboard/
git commit -m "feat(dashboard): add home screen with task counts by status"

# 12. Pantallas de tareas
git add app/src/main/java/ni/edu/uam/uamtasks/ui/screens/tareas/TareasListScreen.kt
git commit -m "feat(tareas): add filterable task list screen"

git add app/src/main/java/ni/edu/uam/uamtasks/ui/screens/tareas/TareaDetailScreen.kt
git commit -m "feat(tareas): add task detail screen with delete confirmation"

git add app/src/main/java/ni/edu/uam/uamtasks/ui/screens/tareas/TareaFormScreen.kt
git commit -m "feat(tareas): add task form with validation and date picker"

# 13. Pantallas de materias
git add app/src/main/java/ni/edu/uam/uamtasks/ui/screens/materias/
git commit -m "feat(materias): add subject CRUD with color picker"

# 14. Documentación final
git add DOCUMENTACION.md COMMITS.md
git commit -m "docs: add project documentation and commits guide"
```

## Si trabajas en equipo

Crea ramas para cada miembro y mezcla con Pull Requests:

```bash
git checkout -b feat/materias
# ... trabajo ...
git push origin feat/materias
# Crear PR en GitHub
```

Esto demuestra **trabajo colaborativo**, otro punto del criterio.

## Antes de entregar

```bash
git log --oneline    # Verifica que los mensajes siguen la convención
git push origin main
```
