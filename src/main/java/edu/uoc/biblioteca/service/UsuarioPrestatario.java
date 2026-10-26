package edu.uoc.biblioteca.service;

import edu.uoc.biblioteca.model.Usuario;

/**
 * Usuario con lo necesario para saber si puede llevarse otro libro.
 * {@code motivo} es null cuando puede; si no, explica por qué no.
 */
public record UsuarioPrestatario(Usuario usuario, long abiertos, String motivo) {

    public boolean puedePedir() {
        return motivo == null;
    }
}
