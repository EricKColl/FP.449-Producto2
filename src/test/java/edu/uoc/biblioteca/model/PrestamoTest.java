package edu.uoc.biblioteca.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PrestamoTest {

    @Test
    void unPrestamoNuevoEstaActivoDuranteElPlazo() {
        Prestamo prestamo = new Prestamo();

        assertThat(prestamo.getFechaPrestamo()).isEqualTo(LocalDate.now());
        assertThat(prestamo.getFechaDevolucionPrevista()).isEqualTo(LocalDate.now().plusDays(Prestamo.DIAS_PRESTAMO));
        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.ACTIVO);
    }

    @Test
    void elUltimoDiaDelPlazoSigueActivo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaDevolucionPrevista(LocalDate.now());

        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.ACTIVO);
    }

    @Test
    void sinDevolverYFueraDePlazoEstaVencido() {
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(LocalDate.now().minusDays(20));
        prestamo.setFechaDevolucionPrevista(LocalDate.now().minusDays(5));

        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.VENCIDO);
    }

    @Test
    void conFechaDeDevolucionEstaDevuelto() {
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(LocalDate.now().minusDays(20));
        prestamo.setFechaDevolucionPrevista(LocalDate.now().minusDays(5));
        prestamo.setFechaDevolucion(LocalDate.now().minusDays(2));

        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.DEVUELTO);
        assertThat(prestamo.isDevuelto()).isTrue();
    }
}
