package edu.uoc.biblioteca.model;

/**
 * Estado de un préstamo. No se guarda en la base de datos: se calcula a partir de sus fechas
 * (ver {@link Prestamo#getEstado()}).
 */
public enum EstadoPrestamo {

    ACTIVO("Activo"),
    VENCIDO("Vencido"),
    DEVUELTO("Devuelto");

    private final String etiqueta;

    EstadoPrestamo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
