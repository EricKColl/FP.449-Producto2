package edu.uoc.biblioteca.controller;
import edu.uoc.biblioteca.model.EstadoPrestamo;
import edu.uoc.biblioteca.model.Prestamo;
import edu.uoc.biblioteca.service.PrestamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }
    // Listado, con filtro opcional: /prestamos?estado=VENCIDO
    @GetMapping
    public String listar(@RequestParam(required = false) EstadoPrestamo estado, Model model) {
        model.addAttribute("prestamos", prestamoService.listar(estado));
        model.addAttribute("estados", EstadoPrestamo.values());
        model.addAttribute("estadoSeleccionado", estado);
        return "prestamos/lista";
    }
    // Registrar la devolución de un préstamo
    @PostMapping("/{id}/devolver")
    public String devolver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Prestamo prestamo = prestamoService.registrarDevolucion(id);
            redirectAttributes.addFlashAttribute("mensajeExito",
                    "Devolución registrada: «" + prestamo.getLibro().getTitulo() + "»");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/prestamos";
    }
}