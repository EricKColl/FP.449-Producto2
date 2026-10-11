package edu.uoc.biblioteca.controller;

import edu.uoc.biblioteca.service.PanelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final PanelService panelService;

    public InicioController(PanelService panelService) {
        this.panelService = panelService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("resumen", panelService.obtenerResumen());
        return "index";
    }
}
