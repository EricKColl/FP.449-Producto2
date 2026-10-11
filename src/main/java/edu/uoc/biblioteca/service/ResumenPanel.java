package edu.uoc.biblioteca.service;

/**
 * Cifras que se muestran en el panel de inicio.
 */
public record ResumenPanel(
        long libros,
        long autores,
        long usuarios,
        long prestamosPendientes,
        long prestamosVencidos) {
}
