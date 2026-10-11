# Biblioteca · Producto 2

Aplicación web de gestión de una biblioteca: catálogo de libros y autores, usuarios y préstamos.
Proyecto en equipo de la asignatura FP065 *Aplicación backend con tecnología Java en servidores de aplicaciones* (UOC).

Este producto parte del [Producto 1](https://github.com/EricKColl/FP.449-Producto1) y añade el primer MVC completo:
base de datos MariaDB, modelos con Spring Data JPA, controladores y vistas Thymeleaf.

- 📄 [Enunciado y rúbrica](docs/enunciado.md)
- 👥 [Reparto de tareas y calendario](docs/reparto-tareas.md)
- 🗂️ [Modelo de datos (E/R y relacional)](docs/modelo-datos.md)
- 📐 [Guía de contribución y convenciones](CONTRIBUTING.md)

## Tecnologías

| Componente | Versión |
|---|---|
| Java (Eclipse Temurin) | 21 LTS |
| Spring Boot | 4.1.1 (Web MVC, Thymeleaf, Data JPA, Validation, Actuator) |
| Base de datos | MariaDB 11.8 (H2 en memoria para los tests) |
| Interfaz | Thymeleaf + Bootstrap 5.3 + Bootstrap Icons (WebJars) |
| Gestión del proyecto | Maven (wrapper incluido) |
| Contenedores | Docker y Docker Compose |

## Requisitos

- **JDK 21** (Eclipse Temurin), con `JAVA_HOME` apuntando a él.
  - Windows: `winget install --id EclipseAdoptium.Temurin.21.JDK -e`
  - macOS: `brew install --cask temurin@21`
- **Git**.
- **Docker Desktop** en marcha.

No hace falta instalar Maven ni MariaDB: el proyecto incluye el wrapper de Maven y la base de datos corre en Docker.

## Puesta en marcha

### Opción A · Todo en Docker (como en la entrega)

```bash
docker compose up -d --build
```

| Servicio | URL | |
|---|---|---|
| Aplicación | http://localhost:8080 | |
| Adminer (gestor de la BD) | http://localhost:8081 | Servidor `db` · usuario `biblioteca` · contraseña `biblioteca` · BD `biblioteca` |
| MariaDB | `localhost:3307` | Para MySQL Workbench, HeidiSQL, DBeaver o IntelliJ |

```bash
docker compose ps          # biblioteca y biblioteca-db deben aparecer como (healthy)
docker compose logs -f app # logs de la aplicación
docker compose down        # parar (los datos se conservan)
docker compose down -v     # parar y borrar los datos (se recrean desde los scripts al arrancar)
```

### Opción B · Desarrollo: la BD en Docker y la aplicación en el IDE

```bash
docker compose up -d db adminer
./mvnw spring-boot:run          # Windows: .\mvnw.cmd spring-boot:run
```

O ejecutar `BibliotecaApplication` desde IntelliJ. Con DevTools, los cambios en las plantillas se ven al recargar la página.

### Opción C · Sin Docker (base de datos en memoria)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Útil para trabajar en las vistas sin Docker. Usa los mismos scripts SQL; los cambios se pierden al parar.

### Tests

```bash
./mvnw verify               # Windows: .\mvnw.cmd verify
```

No necesitan Docker: se ejecutan sobre H2 con los scripts de MariaDB, y fallan si alguna entidad no coincide con las tablas.
GitHub Actions los pasa en cada pull request y además levanta el `docker compose` completo.

## Qué hay en Docker

`docker-compose.yml` levanta tres contenedores:

- **db** · MariaDB 11.8 con `utf8mb4`. La primera vez ejecuta `src/main/resources/db/01-esquema.sql` y
  `02-datos.sql` (carpeta montada en `/docker-entrypoint-initdb.d`). Los datos persisten en el volumen `datos-mariadb`.
- **app** · la aplicación, compilada con el `Dockerfile` multi-etapa (Maven + JDK 21 → JRE 21 alpine, usuario sin
  privilegios). Espera a que la BD esté *healthy* y recibe la conexión por variables de entorno
  `SPRING_DATASOURCE_*`. Su healthcheck usa `/actuator/health`, que también comprueba la conexión con la BD.
- **adminer** · gestor web de la base de datos.

Los valores por defecto (usuarios, contraseñas y puertos) se pueden cambiar copiando `.env.example` a `.env`.
La BD se publica en el **3307** del equipo para no chocar con un MySQL o XAMPP local en el 3306.

## Estructura

```
src/main/java/edu/uoc/biblioteca/
├── BibliotecaApplication.java
├── model/          # entidades JPA: Autor, Libro, Usuario, Prestamo
├── repository/     # repositorios Spring Data
├── service/        # lógica de negocio
└── controller/     # controladores MVC
src/main/resources/
├── db/             # 01-esquema.sql y 02-datos.sql
├── templates/      # vistas Thymeleaf (fragments/layout.html = cabecera, menú y pie comunes)
├── static/css/     # estilos propios
└── application.properties, application-h2.properties
docs/               # enunciado, reparto, modelo de datos, diagramas y mockups
```

## Abrir el proyecto en IntelliJ IDEA

1. *File → Open* y seleccionar el `pom.xml` → *Open as Project*.
2. *File → Project Structure → Project → SDK*: JDK 21 (Temurin).
3. *Settings → Editor → File Encodings*: UTF-8.
4. `docker compose up -d db adminer` y ejecutar `BibliotecaApplication`.

## Flujo de trabajo con Git

- `main`: versión estable. Protegida; solo recibe cambios mediante pull request revisada.
- `develop`: rama de integración. También protegida.
- `feature/<tarea>`: una rama por tarea, creada desde `develop`.

```bash
git switch develop
git pull
git switch -c feature/crud-libros
# ... cambios ...
./mvnw verify
git add .
git commit -m "Añade el listado de libros"
git push -u origin feature/crud-libros
```

Después se abre una pull request hacia `develop` y otro miembro del equipo la revisa antes de fusionarla.

Los fines de línea se normalizan con `.gitattributes`. En Windows se recomienda `git config --global core.autocrlf true`
y en macOS `git config --global core.autocrlf input`.

## Equipo

- Erick Coll Rodríguez
- Carles Miguel Millán
- Jacobo Barrera Toba
- Antonio Charneco Alcalá
