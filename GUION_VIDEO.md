# Guion del video demostrativo — UAM Tasks (máx. 5 min)

Este guion cubre **todos los puntos que el enunciado pide demostrar**:
funcionamiento general, navegación, manejo de estado y funcionalidades
principales.

---

## 🎬 Minuto 0:00 – 0:30 · Introducción (30 s)

> "Buenos días, somos [nombres del equipo] y vamos a presentar **UAM Tasks**,
> nuestro proyecto del curso de Desarrollo de Aplicaciones Móviles.
>
> UAM Tasks es un gestor de tareas académicas que permite a los estudiantes
> organizar sus deberes universitarios por materia, con fecha de entrega,
> prioridad y estado.
>
> Está desarrollada en **Kotlin con Jetpack Compose**, usa el patrón
> **MVVM**, persistencia local con **Room** y navegación con
> **Navigation Compose**."

📌 Mientras hablas: muestra el logo de la app o el README en GitHub.

---

## 🎬 Minuto 0:30 – 1:00 · Estructura del proyecto (30 s)

> "Antes de la demo, mostramos cómo está organizado el proyecto."

📌 Abre Android Studio y muestra el árbol del proyecto. Recorre rápidamente:

- `data/model/` → entidades.
- `data/local/` → Room.
- `data/repository/` → capa de acceso.
- `viewmodel/` → lógica de presentación.
- `ui/navigation/` → rutas.
- `ui/screens/` → pantallas.
- `ui/components/` → componentes reutilizables.

> "Cada capa tiene una responsabilidad clara, siguiendo el patrón MVVM."

---

## 🎬 Minuto 1:00 – 1:45 · Dashboard y navegación (45 s)

📌 Lanza la app en el emulador.

> "Al abrir la app entramos al **Dashboard**. Aquí vemos el conteo total de
> tareas y el desglose por estado: pendientes, en progreso y entregadas.
> Estos datos se actualizan **en tiempo real** desde la base de datos."

📌 Pulsa el botón **Nueva tarea** y vuelve.

> "Desde aquí podemos crear una tarea o navegar a la lista completa."

📌 Pulsa la pestaña **Tareas** del bottom bar.

> "Usamos un **bottom navigation bar** con tres pestañas: Inicio, Tareas y
> Materias. La navegación está implementada con Navigation Compose y rutas
> tipadas."

---

## 🎬 Minuto 1:45 – 2:45 · CRUD de tareas (60 s)

📌 En la pestaña Tareas:

**Crear:**
> "Pulso el botón flotante para crear una nueva tarea."

📌 Llena el formulario:
- Título: "Examen final de Compose"
- Descripción: "Repasar State y Recomposición"
- Materia: seleccionar "Programación Móvil"
- Fecha: abrir el DatePicker y elegir una fecha
- Prioridad: Alta
- Estado: Pendiente

> "El formulario tiene validaciones: el título y la materia son obligatorios.
> Si intento guardar sin llenarlos, muestra el error."

📌 Demuestra una validación (intenta guardar sin título).

📌 Guarda la tarea.

> "La tarea aparece automáticamente en la lista. Esto es porque la lista
> está observando un **StateFlow** del Room mediante Flow reactivos."

**Leer:**
📌 Toca la tarea recién creada.

> "El detalle muestra toda la información de la tarea con su materia, fecha
> formateada y descripción."

**Editar:**
📌 Pulsa el ícono de editar.

> "El mismo formulario se reutiliza para edición, precargado con los datos."

📌 Cambia el estado a "En progreso" y guarda.

**Eliminar:**
📌 Pulsa el ícono de eliminar.

> "Eliminar pide confirmación con un diálogo, para evitar accidentes."

📌 Confirma. La tarea desaparece de la lista.

---

## 🎬 Minuto 2:45 – 3:30 · Filtros y estado compartido (45 s)

> "Demos los filtros. Puedo filtrar por estado..."

📌 Toca el chip **Pendiente**. La lista se reduce.

> "...o por materia."

📌 Toca un chip de materia. La lista se reduce más.

📌 Vuelve al Dashboard.

> "Fíjense que al volver al Dashboard los **contadores reflejan los cambios**
> que hicimos. Esto demuestra que el estado se comparte entre pantallas
> mediante un mismo ViewModel."

---

## 🎬 Minuto 3:30 – 4:15 · CRUD de materias (45 s)

📌 Pestaña **Materias**.

> "Las materias son otra entidad con CRUD completo, relacionada con las
> tareas mediante foreign key."

📌 Crea una nueva materia: "Inteligencia Artificial", código "ISW-601",
docente "Ing. García".

> "Cada materia tiene un selector de color para identificarla visualmente."

📌 Elige un color y guarda.

📌 Vuelve a Tareas, crea una tarea con esa nueva materia.

> "Notemos cómo la nueva materia aparece inmediatamente en el dropdown del
> formulario, gracias al estado compartido."

---

## 🎬 Minuto 4:15 – 4:45 · Persistencia (30 s)

> "Lo último que queremos demostrar es la **persistencia**."

📌 Cierra la app completamente (swipe del recents).

📌 Vuelve a abrirla.

> "Los datos siguen allí. Esto es porque están guardados en **SQLite
> mediante Room**, no solo en memoria."

📌 Muestra que las tareas creadas siguen visibles.

---

## 🎬 Minuto 4:45 – 5:00 · Cierre (15 s)

> "En resumen, UAM Tasks cumple con todos los requisitos del enunciado:
>
> - CRUD completo de dos entidades relacionadas.
> - Siete pantallas con navegación tipada.
> - Manejo de estado reactivo con StateFlow.
> - Persistencia en Room.
> - Tema Material 3, componentes reutilizables y validaciones.
>
> Muchas gracias."

---

## 💡 Tips para grabar

- **Resolución**: graba el emulador a 1080p (View > Tool Windows > Running
  Devices > expandir).
- **Voz**: usa un script (este) y graba el audio aparte si puedes.
- **Software gratis**: OBS Studio, ShareX (Windows), o el grabador integrado
  de Android Studio.
- **Atajos**: practica con `Cmd+R` / `Shift+F10` para correr más rápido.
- **No dejes** el dashboard vacío: la app trae datos de ejemplo sembrados,
  así que arranca con contenido visible.
