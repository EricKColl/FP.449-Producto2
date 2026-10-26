package edu.uoc.biblioteca.service;

import edu.uoc.biblioteca.model.Autor;
import edu.uoc.biblioteca.repository.AutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listar() {
        return autorRepository.findAllByOrderByApellidosAscNombreAsc();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el autor " + id));
    }

    @Transactional
    public Autor crear(Autor autor) {
        autor.setId(null);
        return autorRepository.save(autor);
    }

    @Transactional
    public Autor actualizar(Long id, Autor datos) {
        Autor autor = buscarPorId(id);
        autor.setNombre(datos.getNombre());
        autor.setApellidos(datos.getApellidos());
        autor.setNacionalidad(datos.getNacionalidad());
        autor.setFechaNacimiento(datos.getFechaNacimiento());
        return autor;
    }

    /** Un autor con libros no se borra: cada libro del catálogo debe conservar al menos un autor. */
    @Transactional
    public Autor eliminar(Long id) {
        Autor autor = buscarPorId(id);
        if (!autor.getLibros().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar a " + autor.getNombreCompleto()
                    + " porque tiene libros en el catálogo");
        }
        autorRepository.delete(autor);
        return autor;
    }
}
