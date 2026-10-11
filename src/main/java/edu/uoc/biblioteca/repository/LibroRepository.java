package edu.uoc.biblioteca.repository;

import edu.uoc.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    /** Catálogo ordenado por título, cargando los autores en la misma consulta. */
    @EntityGraph(attributePaths = "autores")
    List<Libro> findAllByOrderByTituloAsc();

    boolean existsByIsbn(String isbn);
}
