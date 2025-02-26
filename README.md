# Proyecto de final de curso para DAM2-PMDM
Jorge Moreno

## ¿Qué es esta aplicación?

Una aplicación de lista de tareas que permite dividir la tarea en elementos más pequeños.

Las tareas se subdividen de la siguiente manera:

```
Categoría
    L Meta
       L Tarea
```

Una **categoría** es un objetivo a gran escala o un conjunto de objetivos similares.

Una **meta** es un objetivo único a completar.

Una **tarea** es un paso necesario para completar ese objetivo.

Además, incluye un ajuste que permite hacer sonar un aplauso al marcar una tarea como completada.

## Estructura del código

El código se divide en las siguientes clases:

- Paquetes de actividad: cada uno tiene un Activity (que solo contiene un fragmento), un Fragment, y un RecyclerView para mostrar listas de datos. Los paquetes son:
    - `main` - Actividad principal: Lista todas las categorías, permitiendo crear/borrar categorías, colapsar los datos si es necesario para no usar mucho espacio, acceder a los ajustes con el menú de la AppBar, y entrar a la actividad de editar categorías específicas.
    - `categoria_edit` - Editar categoría: Permite editar el nombre y la descripción de una categoría, así como crear o borrar metas dentro de la misma.
    - `meta_edit` - Editar meta: Equivalente al anterior, excepto sobre metas y tareas, y además permite editar directamente los datos de una tarea.
- Paquete de la actividad de ajustes (`ajustes`): Contiene el Activity y el Fragment necesarios para editar los ajustes.
- Paquete de diálogos: Contiene dos plantillas de diálogos - `YesNo`, para un diálogo simple de respuesta y/n, y `CreateWithName`, para elegir el nombre de un elemento a crear.
- Paquete de tipos de datos (`data`): Contiene la definición de las tres clases que se usan como datos en esta aplicación: `Categoria`, `Meta` y `Tarea`
- Clase `BaseDatos`: Contiene la lógica de guardado/cargado de datos, usando una BD SQLite.
- Clase `Global`: Contiene datos que es necesario persistir entre actividades. Se refrescan los datos cuando sea posible, para evitar problemas con el GC.

## Más información

Para ver el progreso a lo largo del tiempo, ver [CHANGELOG.md](./CHANGELOG.md). Para ver una lista de objetivos (incluyendo algunos que he tenido que quitar por scope creep), ver [TODO.md](TODO.md)
