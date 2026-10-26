package edu.uoc.biblioteca.service;

/**
 * Cifras que se muestran en el panel de inicio.
 */
public record ResumenPanel(
        long libros,
        long autores,
        long usuarios,
        long prestamosPendientes,
        long prestamosVencidos,
        long prestamosTotales) {

    public long prestamosActivos() {
        return prestamosPendientes - prestamosVencidos;
    }

    public long prestamosDevueltos() {
        return prestamosTotales - prestamosPendientes;
    }

    /** Porcentaje de un tipo de préstamo sobre el total, para la barra de estados. */
    public long porcentaje(long cantidad) {
        return prestamosTotales == 0 ? 0 : Math.round(cantidad * 100.0 / prestamosTotales);
    }
}
