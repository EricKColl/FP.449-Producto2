package edu.uoc.biblioteca.service;

import edu.uoc.biblioteca.repository.AutorRepository;
import edu.uoc.biblioteca.repository.LibroRepository;
import edu.uoc.biblioteca.repository.PrestamoRepository;
import edu.uoc.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class PanelService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    public PanelService(LibroRepository libroRepository, AutorRepository autorRepository,
                        UsuarioRepository usuarioRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public ResumenPanel obtenerResumen() {
        return new ResumenPanel(
                libroRepository.count(),
                autorRepository.count(),
                usuarioRepository.count(),
                prestamoRepository.countByFechaDevolucionIsNull(),
                prestamoRepository.countByFechaDevolucionIsNullAndFechaDevolucionPrevistaBefore(LocalDate.now()));
    }
}
