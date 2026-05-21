# Documentación del Proyecto — UAM Tasks

**Universidad Americana (UAM)**
**Curso:** Desarrollo de Aplicaciones Móviles
**Equipo:** 5
**Fecha:** _[20 de Mayo]_

---

## 1. Tema de la aplicación

**UAM Tasks** es un gestor de tareas académicas pensado para estudiantes
universitarios. Permite organizar las **tareas** que se asignan en cada clase,
agrupándolas por **materia**, con su **fecha de entrega**, su **prioridad**
y su **estado de avance**.

La elección del tema responde al criterio del enunciado de representar un
**caso práctico del entorno real**: la organización de la carga académica
es un problema cotidiano para todo estudiante.

## 2. Funcionalidades implementadas

### 2.1 CRUD completo

| Operación | Tareas | Materias |
|---|:---:|:---:|
| Crear | ✅ Formulario con validaciones | ✅ Formulario con validaciones |
| Leer (listar) | ✅ Lista filtrable + detalle | ✅ Lista con cards |
| Actualizar | ✅ Mismo formulario reutilizado | ✅ Mismo formulario reutilizado |
| Eliminar | ✅ Con diálogo de confirmación | ✅ Con diálogo de confirmación |

### 2.2 Pantallas

La aplicación cuenta con **7 pantallas/destinos de navegación**:

1. **Dashboard (Inicio)** — Tarjeta destacada con el total de tareas y tres
   tarjetas de conteo por estado (Pendientes / En progreso / Entregadas).
2. **Lista de Tareas** — Lista con filtros por estado y por materia, FAB para
   agregar y card que muestra título, materia, estado, prioridad y fecha.
3. **Detalle de Tarea** — Vista expandida con todos los datos y acciones de
   editar/eliminar (con diálogo de confirmación).
4. **Formulario de Tarea (Nueva)** — Formulario validado con DatePicker.
5. **Formulario de Tarea (Editar)** — Mismo composable, precargado con los
   datos existentes.
6. **Lista de Materias** — CRUD de materias, cada una con su color
   identificador.
7. **Formulario de Materia** — Crear/editar con selector visual de color.

### 2.3 Navegación

- **Bottom Navigation Bar** entre las 3 pestañas principales: Inicio, Tareas,
  Materias.
- **Navegación jerárquica** con Navigation Compose y rutas tipadas
  (`sealed class Screen`).
- **Paso de argumentos** seguro vía `navArgument(NavType.LongType)` para
  abrir el detalle y el formulario de edición.
- Botón de **volver** en cada pantalla secundaria.

### 2.4 Manejo de estado

- **MVVM** con `ViewModel` que sobreviven a la rotación de pantalla.
- **`StateFlow`** expuesto por los ViewModels.
- **`collectAsStateWithLifecycle()`** en Compose: la suscripción se cancela
  cuando la pantalla no está visible.
- **Estado compartido**: los dos ViewModels se crean una sola vez en
  `AppNavigation` y se pasan a las pantallas que los necesiten, garantizando
  consistencia entre la lista, el detalle y el formulario.
- **Operadores de Flow** (`combine`, `stateIn`) para componer filtros y
  conteos sin recalcular manualmente.

### 2.5 Persistencia

- Base de datos **SQLite gestionada por Room**.
- Tablas: `materias`, `tareas` (con `FOREIGN KEY` y `ON DELETE CASCADE`).
- `TypeConverters` para serializar los enums `Prioridad` y `EstadoTarea`.
- **Datos de ejemplo** sembrados la primera vez que se crea la base de datos
  (3 materias, 4 tareas) para que la app no luzca vacía en el video.

### 2.6 Validaciones

- **Título de tarea** obligatorio.
- **Materia** obligatoria para crear una tarea.
- **Nombre y código** obligatorios para crear una materia.
- Si no existen materias registradas, el botón "Guardar tarea" se deshabilita
  y se muestra un mensaje guía.

### 2.7 Componentes reutilizables

- `TareaCard`, `MateriaCard`, `EstadoChip`, `PrioridadChip`, `EmptyState`,
  `BottomNavBar`. Todos en `ui/components/` para evitar duplicación.

## 3. Tecnologías utilizadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Kotlin 2.2.10 |
| Interfaz | Jetpack Compose con Material 3 (Compose BOM 2026.02.01) |
| Navegación | Navigation Compose 2.8.5 |
| Estado | ViewModel + StateFlow + Coroutines |
| Persistencia | Room 2.6.1 (con KSP 2.2.10-2.0.2) |
| Iconos | Material Icons Extended |
| Build | Gradle 9.x + AGP 9.2.1 + Version Catalog |
| IDE | Android Studio Ladybug |
| Compatibilidad | minSdk 24, targetSdk 36 |

## 4. Estructura del proyecto

El proyecto sigue el patrón **Clean Architecture simplificado / MVVM**:

```
data/   → Capa de datos (modelos, Room, repositorios)
ui/     → Capa de presentación (Compose, navegación, tema, componentes)
viewmodel/ → Lógica de presentación y exposición de estado
```

Cada capa solo conoce a la capa inferior, lo que facilita el mantenimiento
y las pruebas.

## 5. Decisiones de diseño

- **Room en lugar de Retrofit**: Como la app no depende de un servidor, se
  optó por persistencia local. Los datos sobreviven al cierre de la app,
  cumpliendo el criterio de "persistencia de datos".
- **Navigation Compose**: Se eligió la navegación tipada con rutas
  centralizadas en `Screen.kt` para evitar strings sueltos y errores de
  enrutado.
- **Factory manual de ViewModels**: Se evitó añadir Hilt/Dagger para mantener
  el proyecto autocontenido y fácil de leer. La factoría usa
  `viewModelFactory` de AndroidX.
- **Tema institucional**: Se usa el azul `#1A3A5C` como color primario, con
  acentos en verde/naranja para estados y prioridades.

## 6. Cómo ejecutar

1. Abrir el proyecto en **Android Studio**.
2. Esperar la sincronización de Gradle (descarga ~150 MB la primera vez).
3. Crear un emulador con **API 24+** o conectar un dispositivo físico.
4. Pulsar **Run ▶** (Shift+F10).

## 7. Capturas (sugerencia para el video/documento final)

Reemplaza estos placeholders con capturas reales antes de entregar:

- [ ] Dashboard con datos sembrados.
- [ ] Lista de tareas con un filtro activo.
- [ ] Formulario de tarea con el DatePicker abierto.
- [ ] Detalle de tarea.
- [ ] Lista de materias.
- [ ] Diálogo de confirmación de eliminación.

---

*Documento generado como entregable del proyecto.*
