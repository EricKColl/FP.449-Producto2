# Reparto de tareas · Producto 2

Entrega: **domingo 1 de noviembre de 2026, 23:59**. Fecha interna de entrega: **sábado 31 de octubre** (un día de margen).

El reparto sigue la rúbrica (5 criterios × 5 puntos): cada persona es responsable de **un criterio completo** y de
**una parte vertical de la aplicación** (servicio + controlador + vistas + tests), así se puede trabajar en paralelo
sin pisarse. La asignación de nombres es una propuesta: si alguien prefiere otra parte, se intercambia sin problema.

| | Persona | Parte | Criterio de la rúbrica del que es responsable |
|---|---|---|---|
| **1** | Erick Coll | Base técnica: modelo de datos, Docker, layout común. Integración final | Modelos con Spring Data · Docker |
| **2** | Carles Miguel | CRUD completo de **Libros** | CRUD con Spring |
| **3** | Jacobo Barrera | **Autores** y **Usuarios** + diagrama E/R y modelo relacional + vídeo | Diagrama E/R |
| **4** | Antonio Charneco | **Mockup** + **Préstamos** + memoria final | Mockup |

Las cuatro partes llevan una carga parecida (unas 9-10 horas cada una). La primera va antes porque las demás dependen de ella.

---

## Persona 1 · Base técnica (Erick) · ✅ hecha el 11 de octubre

- [x] Proyecto migrado desde el Producto 1 (Spring Boot 4.1.1, Java 21).
- [x] Dependencias nuevas: Spring Data JPA, Validation, MariaDB, H2, Actuator, Bootstrap e iconos como WebJars.
- [x] Esquema SQL a partir del E/R + datos de prueba (`src/main/resources/db`).
- [x] Entidades `Autor`, `Libro`, `Usuario`, `Prestamo` con sus relaciones (N:M y 1:N) + repositorios.
- [x] Docker: `docker-compose.yml` con MariaDB, aplicación y Adminer; healthchecks; volumen persistente.
- [x] Layout común Thymeleaf (cabecera, menú, mensajes, pie), panel de inicio con cifras y página de error.
- [x] Tests (18) e integración continua en GitHub Actions.
- [x] Documentación: `README.md`, `CONTRIBUTING.md`, `docs/`.

**Pendiente durante el proyecto**
- [x] Crear las ramas `main`, `develop` y la rama de trabajo de cada persona.
- [ ] Proteger `main` y `develop` (igual que en el Producto 1).
- [ ] Revisar y fusionar las pull requests; resolver conflictos; mantener Docker funcionando.
- [ ] Prueba final desde un clon limpio: `docker compose down -v && docker compose up -d --build`.
- [ ] Memoria: apartados de modelo de datos y Docker.
- [ ] Preparar el ZIP de entrega.

---

## Persona 2 · CRUD completo de Libros (Carles)

Rama: `feature/carles-crud-libros`

Es el criterio que más puntos asegura: el CRUD **tiene que estar completo y funcionando**.

**Ficheros**: `service/LibroService.java`, `controller/LibroController.java`, `templates/libros/lista.html`,
`templates/libros/formulario.html`, `templates/libros/detalle.html`, `test/.../controller/LibroControllerTest.java`

| Acción | Ruta |
|---|---|
| Listar (con búsqueda por título, ISBN o autor) | `GET /libros` (`?q=`) |
| Ver detalle: autores, ejemplares disponibles, historial de préstamos | `GET /libros/{id}` |
| Formulario de alta | `GET /libros/nuevo` |
| Crear | `POST /libros` |
| Formulario de edición | `GET /libros/{id}/editar` |
| Actualizar | `POST /libros/{id}` |
| Eliminar, con confirmación (modal de Bootstrap) | `POST /libros/{id}/eliminar` |

- [ ] Formulario con validación (`@Valid` + `BindingResult`) y los errores bajo cada campo.
- [ ] Selección múltiple de **autores** (es la relación N:M). Lo más sencillo es recibir los ids con
      `@RequestParam(required = false) List<Long> autorIds` y asignarlos con `autorRepository.findAllById(autorIds)`.
- [ ] ISBN duplicado → error en el campo (`existsByIsbn`), no una excepción.
- [ ] No permitir borrar un libro con préstamos (`prestamoRepository.existsByLibroId`) → `mensajeError`.
- [ ] Mensajes flash de éxito al crear, editar y eliminar.
- [ ] Tests MockMvc: listar, crear válido (redirige), crear inválido (vuelve al formulario), eliminar con préstamos.
- [ ] Memoria: apartado del CRUD, con capturas.

---

## Persona 3 · Autores, Usuarios y diagrama E/R (Jacobo)

Rama: `feature/jacobo-autores-usuarios`

**Ficheros**: `service/AutorService.java`, `controller/AutorController.java`, `templates/autores/lista.html`,
`templates/autores/detalle.html`, `service/UsuarioService.java`, `controller/UsuarioController.java`,
`templates/usuarios/lista.html`, `templates/usuarios/detalle.html`, `docs/diagramas/`

| Acción | Ruta |
|---|---|
| Listado de autores (nº de libros de cada uno) | `GET /autores` |
| Detalle de autor con sus libros | `GET /autores/{id}` |
| Listado de usuarios | `GET /usuarios` |
| Detalle de usuario con su historial de préstamos y su estado | `GET /usuarios/{id}` |

- [ ] Diagrama E/R en notación de Chen (draw.io): las 4 entidades con **todos** sus atributos, claves primarias
      subrayadas, relaciones con cardinalidades y la **N:M** bien marcada. Fuente `.drawio` + `er.png`.
- [ ] Modelo relacional: MySQL Workbench → *Database → Reverse Engineer* contra `localhost:3307`
      (usuario `biblioteca` / `biblioteca`) → exportar `relacional.png`.
- [ ] Los dos diagramas deben coincidir con `docs/modelo-datos.md`.
- [ ] Tests MockMvc de los listados.
- [ ] Extra si da tiempo: alta y edición de usuarios (mismo patrón que el CRUD de libros).
- [ ] **Vídeo** (5-8 min) cuando todo esté en `develop` (ver guion al final).
- [ ] Memoria: apartado del diagrama E/R y del modelo relacional.

---

## Persona 4 · Mockup, Préstamos y memoria (Antonio)

Rama: `feature/antonio-mockup-prestamos`

**El mockup va primero** (antes del jueves 15 de octubre): sirve de guía a las vistas de los demás.

- [ ] Mockup (Figma, Balsamiq, Pencil o draw.io) que siga el estilo del layout ya hecho (Bootstrap, azul `#1f4e79`):
  - panel de inicio · listado de libros · formulario de libro · detalle de libro · listado de usuarios · listado de préstamos
  - **menú de navegación** en todas las pantallas y **botones** (nuevo, ver, editar, eliminar, volver, registrar devolución)
  - exportar las pantallas a `docs/mockup/*.png`
- [ ] Préstamos: `service/PrestamoService.java`, `controller/PrestamoController.java`, `templates/prestamos/lista.html`
  - listado con usuario, libro, fechas y **estado** con color (activo, vencido, devuelto) → `GET /prestamos`
  - filtro por estado → `GET /prestamos?estado=VENCIDO`
  - registrar la devolución → `POST /prestamos/{id}/devolver` (pone la fecha de hoy + mensaje)
  - extra si da tiempo: nuevo préstamo, solo con libros que tengan ejemplares disponibles
- [ ] Tests MockMvc del listado y de la devolución.
- [ ] **Memoria final**: plantilla común, recoger los apartados de cada uno y montar el PDF
      (trabajo realizado, dificultades, puntos no realizados, propuestas de mejora, autoevaluación con la rúbrica, webgrafía).

---

## Calendario

| Semana | Objetivo |
|---|---|
| **12-18 oct** | Mockup (P4) y borrador del E/R (P3) antes del jueves 15. P2, P3 y P4 empiezan su parte en su rama `feature/*`. |
| **19-25 oct** | Partes terminadas → pull request a `develop` y revisión cruzada. |
| **26-30 oct** | Congelación el miércoles 28. Integración, prueba completa con Docker, vídeo y memoria. |
| **31 oct** | Entrega (un día de margen). |

## Forma de trabajar

- Ramas: `main` (estable) ← `develop` (integración) ← `feature/<tarea>`. Pull request a `develop` revisada por otra persona.
- Antes de abrir la PR: `./mvnw verify` en verde y la app probada en el navegador.
- Las convenciones de código y de vistas están en [`CONTRIBUTING.md`](../CONTRIBUTING.md). Hay que seguirlas para que todas las pantallas se vean y funcionen igual.
- **El modelo es común**: si alguien necesita cambiar una entidad o el SQL, lo avisa antes en el grupo (ver `docs/modelo-datos.md`).

## Definición de "hecho" (para cada parte)

1. Funciona con `docker compose up -d --build` sobre una base de datos recién creada.
2. Usa el layout común (menú, mensajes, pie) y la opción del menú correspondiente queda marcada.
3. Tiene tests y `./mvnw verify` pasa.
4. Está fusionada en `develop` tras una revisión.
5. Tiene su apartado en la memoria, con capturas.

## Guion del vídeo (5-8 min)

1. `docker compose up -d --build` y `docker compose ps` (los tres contenedores en *healthy* / *running*).
2. Adminer (`localhost:8081`): las tablas y la relación `libro_autor`.
3. Panel de inicio → menú de navegación.
4. CRUD de libros completo: crear (con un error de validación), ver, editar, eliminar (y el bloqueo de un libro con préstamos).
5. Autores y usuarios (listados y detalles).
6. Préstamos: filtro por estado y registrar una devolución → el panel se actualiza.
7. Cierre: diagrama E/R y mockup, comparados con la aplicación final.
