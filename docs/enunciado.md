# Producto 2 · Primer MVC (FP065)

> Copia del enunciado y de la rúbrica del aula virtual, para tenerlos siempre junto al código.
> Fecha de entrega: **domingo 1 de noviembre de 2026, 23:59** · 1 intento.

## Descripción

En este producto comenzaremos con el desarrollo de un PROYECTO PROPIO y en EQUIPO que seguirá las indicaciones de un proyecto de gestión de una Biblioteca.

En este producto diseñaremos las primeras pantallas, y para ello deberemos completar el primer MVC con al menos tres tablas con información, por ejemplo usuarios, libros y préstamos, y que se reflejarán en nuestra aplicación mediante sus respectivos modelos, controladores y vistas.

## Objetivos

- Realizar el mockup de la aplicación a partir del cual implementaremos en nuestra aplicación.
- Usar Spring JPA para representar los modelos de nuestra base de datos.
- Conocer y usar los controladores y las vistas dentro de Spring para mostrar la información de la base de datos.

## Pasos a seguir

1. Leer detenidamente estas instrucciones e identificar los requerimientos de la actividad.
2. Revisar detenidamente la rúbrica de evaluación.
3. Consultar los recursos necesarios facilitados en el aula.
4. Realizar un diagrama E/R en el que aparezcan al menos tres entidades, por ejemplo, Libros, Usuarios y Préstamos.
5. Diseñar la interfaz mediante un mockup de la ventana/s que se vayan a desarrollar.
6. Preparar la aplicación para el uso de base de datos. Se propone el uso de MariaDB/MySQL pero se puede implementar con H2, PostgreSQL o bien NoSQL como MongoDB:
   - crear la base de datos y tablas a partir del diagrama E/R
   - configurar `application.properties`
7. Desarrollar las interfaces mediante Spring Boot. Para ello se deberán realizar los siguientes pasos, aunque no siempre en el mismo orden:
   1. Crear el "Model"
   2. Crear el "Repository"
   3. Crear el "Controller"
   4. Crear la vista usando Thymeleaf
   5. Repetir desde "a" tantas veces como sea necesario para todos los modelos y vistas que se hayan implementado en el mockup
8. Al menos se deberá crear el CRUD (Create, Read, Update y Delete) de una de las entidades y sus interfaces.
9. Realizar las modificaciones oportunas del contenedor Docker.

## Requisitos

- Partir de la descripción del programa que se ha descrito en el Producto 1 (repositorio `EricKColl/FP.449-Producto1`).
- Instalar la herramienta de diseño de diagrama de clases.
- Instalar la herramienta de diseño de diagrama modelo-relacional.
- Instalar la base de datos MariaDB o MySQL.
- Instalar el framework de programación Spring o Java EE.

## Entrega

- Por el canal indicado en el aula virtual.
- Formato: ZIP o RAR.
- Nombre del archivo: `producto3(FP065)_Apellido1Apellido2_Nombre` (así aparece en el enunciado, aunque sea el Producto 2).
- Memoria del trabajo: trabajo realizado, dificultades, puntos no realizados, propuestas de mejora, autoevaluación, webgrafía.
- Vídeo o enlace de YouTube mostrando el funcionamiento de la aplicación.

## Rúbrica (máximo 25 puntos, aprobado con 12,5)

| Criterio | 5 puntos (máximo) | 3,75 | 2,5 | 1,25 |
|---|---|---|---|---|
| **Diagrama E/R** | ≥ 3 entidades, todos sus atributos (≥ 2 por entidad) y las relaciones, **una de ellas N:M** | ≥ 3 entidades con atributos y relaciones | ≥ 2 entidades con atributos y relaciones | ≥ 2 entidades con atributos |
| **Mockup** | ≥ 2 interfaces detalladas que presenten **las 3 entidades**, con **menú de navegación y botones de navegación** | ≥ 2 interfaces con 2 entidades, menú y botones | ≥ 1 interfaz con 2 entidades, menú y botones | ≥ 1 interfaz con 2 entidades |
| **Modelos con Spring Data** | ≥ 3 modelos **y sus relaciones** | ≥ 3 modelos | ≥ 2 modelos | ≥ 1 modelo |
| **CRUD con Spring** | Controladores y vistas de **1 CRUD completo** + listado de los demás modelos | READ, CREATE y UPDATE de 1 modelo + listado de los demás | READ y CREATE de 1 modelo + listado de los demás | READ de todos los modelos |
| **Docker** | Modificaciones (nuevas librerías y binarios) para que el proyecto **funcione correctamente** | El proyecto se ejecuta parcialmente y funciona correctamente | Se ejecuta parcialmente, pero su funcionamiento es mejorable | — |
