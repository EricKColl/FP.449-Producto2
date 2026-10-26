package edu.uoc.biblioteca.service;

import edu.uoc.biblioteca.model.EstadoPrestamo;
import edu.uoc.biblioteca.model.Libro;
import edu.uoc.biblioteca.model.Prestamo;
import edu.uoc.biblioteca.model.Usuario;
import edu.uoc.biblioteca.repository.LibroRepository;
import edu.uoc.biblioteca.repository.PrestamoRepository;
import edu.uoc.biblioteca.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LibroRepository libroRepository,
                           UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** Todos los préstamos (del más reciente al más antiguo) o solo los de un estado. */
    public List<Prestamo> listar(EstadoPrestamo estado) {
        List<Prestamo> prestamos = prestamoRepository.findAllByOrderByFechaPrestamoDesc();
        if (estado == null) {
            return prestamos;
        }
        // El estado no está en la base de datos (se calcula con las fechas), por eso se filtra aquí
        return prestamos.stream()
                .filter(prestamo -> prestamo.getEstado() == estado)
                .toList();
    }

    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el préstamo " + id));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAllByOrderByApellidosAscNombreAsc();
    }

    /** Catálogo para el formulario de préstamo, con los ejemplares libres de cada libro. */
    public List<LibroDisponible> listarLibrosConDisponibles() {
        return libroRepository.findAllByOrderByTituloAsc().stream()
                .map(libro -> new LibroDisponible(libro, ejemplaresDisponibles(libro)))
                .toList();
    }

    @Transactional
    public Prestamo crear(Long usuarioId, Long libroId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el usuario " + usuarioId));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el libro " + libroId));
        if (ejemplaresDisponibles(libro) <= 0) {
            throw new IllegalStateException(
                    "No quedan ejemplares disponibles de «" + libro.getTitulo() + "»");
        }
        return prestamoRepository.save(new Prestamo(usuario, libro));
    }

    @Transactional
    public Prestamo registrarDevolucion(Long id) {
        Prestamo prestamo = buscarPorId(id);
        if (prestamo.isDevuelto()) {
            throw new IllegalStateException("Ese préstamo ya estaba devuelto");
        }
        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamo;
    }

    private long ejemplaresDisponibles(Libro libro) {
        return libro.getEjemplares()
                - prestamoRepository.countByLibroIdAndFechaDevolucionIsNull(libro.getId());
    }
}
