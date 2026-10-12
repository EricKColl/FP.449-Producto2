# Reparto de tareas · Producto 2

Entrega: **domingo 1 de noviembre de 2026, 23:59**. Fecha interna de entrega: **sábado 31 de octubre** (un día de margen).

El reparto sigue la rúbrica (5 criterios × 5 puntos): cada persona es responsable de **un criterio completo** y de
**una parte vertical de la aplicación** (servicio + controlador + vistas + tests).

**El código se hace por turnos**, uno detrás de otro (ver [Calendario](#calendario-por-turnos)): cada persona programa
su parte cuando le toca, partiendo del trabajo del anterior ya unido en `develop`. Así nadie tiene que resolver
conflictos al juntar el código. El mockup y los diagramas no esperan turno porque no tocan el código.

| | Persona | Parte | Criterio de la rúbrica del que es responsable |
|---|---|---|---|
| **1** | Erick Coll | Base técnica: modelo de datos, Docker, layout común. Revisión e integración de las pull requests, montaje del vídeo y memoria | Modelos con Spring Data · Docker |
| **2** | Carles Miguel | CRUD completo de **Libros** | CRUD con Spring |
| **3** | Jacobo Barrera | **Autores** y **Usuarios** + diagrama E/R y modelo relacional | Diagrama E/R |
| **4** | Antonio Charneco | **Mockup** + **Préstamos** | Mockup |

La base técnica fue la primera porque todas las demás dependen de ella. Cada parte de código lleva unas 8-10 horas.

**El vídeo es de todo el equipo**: enseña el programa completo funcionando y **cada uno graba la parte que ha hecho**. Erick une los cortes en un solo vídeo (ver [Vídeo](#vídeo-6-8-min-cada-uno-graba-su-parte)).

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
- [ ] Revisar y fusionar las pull requests **en el orden de los turnos** y avisar en el grupo al fusionar cada una
      (es la señal para que empiece el turno siguiente). Mantener Docker funcionando.
- [ ] Prueba final desde un clon limpio: `docker compose down -v && docker compose up -d --build`.
- [ ] Grabar su parte del vídeo (presentación, Docker, Adminer y panel de inicio; y el cierre).
- [ ] Unir los cortes de todos en un solo vídeo y subirlo a YouTube.
- [ ] Memoria: montarla con lo que envíe cada uno (antes del viernes 30), poner el enlace del vídeo y exportar el PDF.
- [ ] Preparar el ZIP de entrega.

---

## Persona 2 · CRUD completo de Libros (Carles)

Rama: `feature/carles-crud-libros` · **Turno 1: del lunes 12 al lunes 19 de octubre** (pull request el lunes 19)

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
| Eliminar, con confirmación | `POST /libros/{id}/eliminar` |

- [ ] Formulario con validación (`@Valid` + `BindingResult`) y los errores bajo cada campo.
- [ ] Selección múltiple de **autores** (es la relación N:M) con `th:field="*{autores}"`: Spring convierte los ids
      marcados en objetos `Autor` (código completo en la guía, apartado 9).
- [ ] ISBN duplicado → error en el campo (`findByIsbn`), no una excepción.
- [ ] No permitir borrar un libro con préstamos (`prestamoRepository.existsByLibroId`) → `mensajeError`.
- [ ] Mensajes flash de éxito al crear, editar y eliminar.
- [ ] Tests MockMvc: listar, crear válido (redirige), crear inválido (vuelve al formulario), eliminar con préstamos.
- [ ] Grabar su parte del vídeo: el CRUD de libros.
- [ ] Memoria: enviar a Erick qué ha hecho y sus dificultades (antes del viernes 30).

---

## Persona 3 · Autores, Usuarios y diagrama E/R (Jacobo)

Rama: `feature/jacobo-autores-usuarios` · **Diagramas: ya** (borrador antes del jueves 15) ·
**Turno 2 (pantallas): del martes 20 al viernes 23 de octubre** (pull request el viernes 23)

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
- [ ] Grabar su parte del vídeo: diagramas, autores y usuarios.
- [ ] Memoria: enviar a Erick qué ha hecho (diagramas y pantallas) y sus dificultades (antes del viernes 30).

---

## Persona 4 · Mockup y Préstamos (Antonio)

Rama: `feature/antonio-mockup-prestamos` · **Mockup: ya** (antes del jueves 15) ·
**Turno 3 (préstamos): del viernes 23 al martes 27 de octubre** (pull request el martes 27)

**El mockup va primero** y no espera turno: sirve de guía a las vistas de los demás.

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
- [ ] Grabar su parte del vídeo: mockup y préstamos.
- [ ] Memoria: enviar a Erick qué ha hecho (mockup y préstamos) y sus dificultades (antes del viernes 30).

---

## Calendario por turnos

| Fecha | Qué | Quién |
|---|---|---|
| Domingo 11 oct | Base técnica en GitHub ✅ | Erick |
| **Lunes 12 – lunes 19 oct** | **Turno 1**: CRUD de libros. El lunes 19, pull request y Erick la une | Carles |
| **Jueves 15 oct** | Mockup terminado y borrador del diagrama E/R, enviados al grupo (no esperan turno) | Antonio, Jacobo |
| **Martes 20 – viernes 23 oct** | **Turno 2**: autores y usuarios + diagramas finales. El viernes 23, pull request | Jacobo |
| **Viernes 23 – martes 27 oct** | **Turno 3**: préstamos + mockup final. El martes 27, pull request | Antonio |
| **Miércoles 28 oct** | Congelación: todo en `develop` y pasado a `main`. Desde aquí, solo arreglos | Erick |
| **Jueves 29 oct** | Cada uno graba su parte del vídeo y se la envía a Erick | Todos |
| **Viernes 30 oct** | Erick une el vídeo y lo sube a YouTube. Cada uno envía a Erick lo de su parte para la memoria | Erick / todos |
| **Sábado 31 oct** | Memoria en PDF, ZIP y cada uno sube la entrega (un día de margen) | Todos |

**Cómo funcionan los turnos**

1. **Nadie programa antes de su turno.** Mientras tanto: dejar el proyecto funcionando en el ordenador (guía, apartados 2 a 5)
   y leer el capítulo propio de la guía.
2. **Erick avisa en el grupo cuando une una pull request**: es la señal de que empieza el turno siguiente.
3. **Al empezar tu turno**, trae a tu rama lo último de `develop`:
   ```
   git switch develop
   git pull
   git switch feature/<tu-rama>
   git merge develop
   git push
   ```
4. **Si no vas a llegar a tu fecha, avisa con un día de margen** para que el siguiente se organice.
5. El mockup y los diagramas se suben a la rama de cada uno en cuanto estén (son ficheros nuevos de `docs/` y no chocan
   con nada) y entran en `develop` con la pull request de su turno.

## Forma de trabajar

- Ramas: `main` (estable) ← `develop` (integración) ← `feature/<tarea>`. Pull request a `develop` revisada por otra persona.
- El código se hace **por turnos** (ver arriba) y las pull requests se unen en ese mismo orden.
- Antes de abrir la PR: `./mvnw verify` en verde y la app probada en el navegador.
- Las convenciones de código y de vistas están en [`CONTRIBUTING.md`](../CONTRIBUTING.md). Hay que seguirlas para que todas las pantallas se vean y funcionen igual.
- **El modelo es común**: si alguien necesita cambiar una entidad o el SQL, lo avisa antes en el grupo (ver `docs/modelo-datos.md`).

## Definición de "hecho" (para cada parte)

1. Funciona con `docker compose up -d --build` sobre una base de datos recién creada.
2. Usa el layout común (menú, mensajes, pie) y la opción del menú correspondiente queda marcada.
3. Tiene tests y `./mvnw verify` pasa.
4. Está fusionada en `develop` tras una revisión.
5. Su autor ha enviado a Erick lo que ha hecho y sus dificultades para la memoria.

## Vídeo (6-8 min): cada uno graba su parte

El vídeo enseña **todo el programa funcionando**. Cada persona graba **lo que ha hecho** y lo explica con su voz;
después Erick une los cortes en un solo vídeo.

| Orden | Quién | Duración | Qué enseña |
|---|---|---|---|
| 1 | Erick | 1:30 | Presentación del equipo y del proyecto. `docker compose up -d --build` y `docker compose ps` (los tres contenedores en marcha). Adminer: las tablas y la relación `libro_autor`. Panel de inicio y menú de navegación. |
| 2 | Carles | 2:00 | CRUD de libros: buscar, crear con un error de validación, crear bien, ver el detalle, editar (con los autores marcados) y eliminar (un libro sin préstamos y el bloqueo de uno con préstamos). |
| 3 | Jacobo | 1:30 | Diagrama E/R y modelo relacional. Autores y usuarios: listados y detalles. |
| 4 | Antonio | 1:30 | Mockup comparado con la aplicación. Préstamos: filtro por estado y registrar una devolución; el panel de inicio se actualiza. |
| 5 | Erick | 0:30 | Cierre: los tests en verde (`verify`) y las comprobaciones automáticas de GitHub. |

**Cómo grabar tu parte**

1. Después de la congelación (miércoles 28), ponte al día: `git switch develop` y `git pull`.
2. Para la aplicación de IntelliJ (■) y arranca todo como en la entrega, con los datos de prueba limpios:
   `docker compose down -v` y `docker compose up -d --build`.
3. Graba la **pantalla completa con micrófono** en formato mp4. Recomendado: **OBS Studio** (Windows y Mac).
   Sin instalar nada: `Cmd`+`Mayús`+`5` en Mac; en Windows, `Win`+`Alt`+`R` graba solo la ventana activa (sirve si solo enseñas el navegador).
4. Habla como si se lo explicaras al profesor: qué haces y por qué funciona así. Ensáyalo una vez antes de grabar.
5. Llama al fichero `video-<orden>-<nombre>.mp4` (por ejemplo `video-2-carles.mp4`) y envíaselo a Erick
   (Google Drive o WeTransfer) **el jueves 29**.

**Montaje (Erick)**: une los cortes en orden con Clipchamp (Windows, viene instalado) o iMovie (Mac); también sirven
Shotcut o DaVinci Resolve en los dos. Sube el vídeo a YouTube como **Oculto** y pasa el enlace al grupo el **viernes 30**:
va en la memoria y en el ZIP de la entrega.
