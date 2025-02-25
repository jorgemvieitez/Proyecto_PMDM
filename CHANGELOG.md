## Enero

### V17/01/2025

- Movido el mockup a este repositorio
- Actualizado el mockup para añadir más layouts (aún quedan más)

### L20/01/2025

- Terminado mockup
- Setup básico de la actividad principal y el toolbar

## V24/01/2025

- Añadido estilo Material (aún tengo que ponerlo en el FAB)
- Creado primer fragmento y añadido a la actividad principal (movido FAB al fragmento)
- Intentos con el RecyclerView para la lista

## V31/01/2025

- Asignado el layout al RecyclerView con un ViewAdapter
    - Tendré que hacer esto unas cuantas veces más
- Creada clase para Categoría (almacenar datos)
- Creado el layout básico del RecyclerView
    - Título (dependiente de Categoria)
    - Botón collapse y contenidos colapsables
    - Botón menú (sin funcionalidad todavía)

## Febrero

## X05/02/2025

- Creado menú en el layout de la lista main
- Creada actividad de editar categoría (vacía de momento)

## V07/02/2025

- Creado fragmento principal de editar categoría (no funcional actualmente)
- Rediseños para ser funcional en el modo oscuro

## V14/02/2025

- Añadido adaptador del RecyclerView para editar categoría, metas se reciben de la categoría original
    - No carga las metas propiamente, hay que comprobar por qué ocurre esto
    - SOLUCIONADO: me faltaba añadir el layoutmanager
- Creado layout de la lista de metas en editar categoría, igual estaría bien añadir formas de reordenar pero igual es muy complicado
- PARA HACER:
    - poner los datos propios
    - ajustar el padding en el recyclerview

## X19/02/2025

- Editada lista de metas, ahora tiene un aspecto mucho mejor y está propiamente padeado
    - Además, muestra el nombre de la meta y tiene los iconos de editar y borrar
    - La funcionalidad de los iconos todavía queda por hacer
- Los campos de nombre y descripción son funcionales y se pueden guardar, editando los datos de la categoría
    - El fragmento en la MainActivity se reinicia al volver, para poder actualizar los datos
- Creada una mínima "descripción de categoría", que si tengo tiempo cambiaré por un RecyclerView propiamente montado
- Arreglados los divisores en la lista de la actividad principal - ni me había dado cuenta de que los había puesto mal
- TODAVÍA TENGO QUE CREAR UN ESTILO PARA LOS FAB, que use los colores de acento

## S22/02/2025

- Añadida funcionalidad de crear y borrar categorías
- Mejorada la UX a la hora de editar categorías y volver a la actividad principal
- Eliminado botón de ver categoría por scope creep
- Añadidas tareas a los modelos
- Añadidos fundamentos de la base de datos SQLite
- Setup para la última actividad (que no ha sido cancelada por scope creep) - que es básicamente una copia del CategoriaEdit
    - Aún necesito añadir funcionalidad de prácticamente todo en esta actividad - está todo super roto

## D23/02/2025
- Añadida funcionalidad de crear metas
- Se pueden ver y editar los datos de las metas
- Creado estilo para los FAB. Oh yeah
- Creada lista de tareas - de momento se pueden borrar y añadir, pero no editar
- Botón de borrar meta muestra un popup antes de borrar
- Añadido `android:launchMode="singleTop"` a todas las actividades para que no se vuelvan a crear al pulsar el botón Up
    - Esto evita un error al pulsar el botón Up en la Activity de editar metas, ya que la Activity de editar categorías necesita parámetros.
    - Es posible que en un futuro necesitemos volver a crear las Activities anidadas (específicamente la de categorías), pero solucionar esta limitación es problema del yo del futuro

## M25/02/2025 (mañana)
- Se pueden editar tareas
    - Solucionado un error en el que el estado de la tarea no se veía reflejado al abrir la actividad
- Añadida más funcionalidad de la BD: patrón singleton, insertar datos dados (quedan por añadir UPDATEs, INSERTs individuales, SELECTs, etc etc)
- Creado fragmento de preferencias - falta linkearlo al menú y añadir la funcionalidad
- Añadir forma de ver si la tarea está completada - si lo está, aparecerá con un tick en la actividad principal, y aparecerá más gris en la actividad de categoría
