package edu.uoc.biblioteca.controller;

import edu.uoc.biblioteca.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("autores", autorService.listar());
        return "autores/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("autor", autorService.buscarPorId(id));
        return "autores/detalle";
    }
}
