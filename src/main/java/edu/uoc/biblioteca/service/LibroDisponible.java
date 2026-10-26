package edu.uoc.biblioteca.service;

import edu.uoc.biblioteca.model.Libro;

/**
 * Libro del catálogo con los ejemplares que se pueden prestar ahora mismo.
 */
public record LibroDisponible(Libro libro, long disponibles) {

    public boolean prestable() {
        return disponibles > 0;
    }
}
