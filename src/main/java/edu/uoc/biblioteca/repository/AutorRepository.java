package edu.uoc.biblioteca.repository;

import edu.uoc.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findAllByOrderByApellidosAscNombreAsc();
}
