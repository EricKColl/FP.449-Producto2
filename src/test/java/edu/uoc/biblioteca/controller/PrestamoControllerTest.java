package edu.uoc.biblioteca.controller;

import edu.uoc.biblioteca.repository.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Préstamos: listado, filtro, devolución y alta, contra la base de datos H2 con los datos de prueba.
 * @Transactional deshace los cambios al terminar cada test.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@Transactional
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Test
    void listaTodosLosPrestamos() throws Exception {
        mockMvc.perform(get("/prestamos"))
                .andExpect(status().isOk())
                .andExpect(view().name("prestamos/lista"))
                .andExpect(content().string(containsString("Cien años de soledad")))
                .andExpect(content().string(containsString("La casa de los espíritus")));
    }

    @Test
    void filtraLosVencidos() throws Exception {
        mockMvc.perform(get("/prestamos").param("estado", "VENCIDO"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("La casa de los espíritus")))
                .andExpect(content().string(not(containsString("Cien años de soledad"))));
    }

    @Test
    void registraUnaDevolucion() throws Exception {
        mockMvc.perform(post("/prestamos/7/devolver"))
                .andExpect(redirectedUrl("/prestamos"))
                .andExpect(flash().attributeExists("mensajeExito"));
        assertThat(prestamoRepository.findById(7L)).get()
                .satisfies(prestamo -> assertThat(prestamo.getFechaDevolucion()).isEqualTo(LocalDate.now()));
    }

    @Test
    void noDevuelveDosVecesElMismoPrestamo() throws Exception {
        mockMvc.perform(post("/prestamos/1/devolver"))
                .andExpect(redirectedUrl("/prestamos"))
                .andExpect(flash().attributeExists("mensajeError"));
    }

    @Test
    void muestraElFormularioDeNuevoPrestamo() throws Exception {
        mockMvc.perform(get("/prestamos/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("prestamos/form"))
                .andExpect(content().string(containsString("Laura Martínez Soler")))
                .andExpect(content().string(containsString("El hobbit")));
    }

    @Test
    void creaUnPrestamoDeUnLibroDisponible() throws Exception {
        long antes = prestamoRepository.count();

        mockMvc.perform(post("/prestamos")
                        .param("usuarioId", "1")
                        .param("libroId", "14"))
                .andExpect(redirectedUrl("/prestamos"))
                .andExpect(flash().attributeExists("mensajeExito"));

        assertThat(prestamoRepository.count()).isEqualTo(antes + 1);
    }

    @Test
    void noPrestaUnLibroSinEjemplaresDisponibles() throws Exception {
        long antes = prestamoRepository.count();

        // Libro 3: 1 ejemplar, ya prestado (préstamo 5)
        mockMvc.perform(post("/prestamos")
                        .param("usuarioId", "1")
                        .param("libroId", "3"))
                .andExpect(redirectedUrl("/prestamos/nuevo"))
                .andExpect(flash().attributeExists("mensajeError"));

        assertThat(prestamoRepository.count()).isEqualTo(antes);
    }

    @Test
    void elFormularioDesactivaLosLibrosSinEjemplares() throws Exception {
        // Libro 3 («La casa de los espíritus»): 1 ejemplar, ya prestado (préstamo 5)
        mockMvc.perform(get("/prestamos/nuevo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("La casa de los espíritus · sin ejemplares")))
                .andExpect(content().string(matchesPattern(
                        "(?s).*<option value=\"3\"\\s+disabled=\"disabled\">La casa de los espíritus.*")))
                .andExpect(content().string(containsString("El juego del ángel · 1 disponible")));
    }
}
