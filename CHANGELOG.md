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