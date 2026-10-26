package edu.uoc.biblioteca.service;
import edu.uoc.biblioteca.model.EstadoPrestamo;
import edu.uoc.biblioteca.model.Prestamo;
import edu.uoc.biblioteca.repository.PrestamoRepository;
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
    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
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
    @Transactional
    public Prestamo registrarDevolucion(Long id) {
        Prestamo prestamo = buscarPorId(id);
        if (prestamo.isDevuelto()) {
            throw new IllegalStateException("Ese préstamo ya estaba devuelto");
        }
        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamo;
    }
}