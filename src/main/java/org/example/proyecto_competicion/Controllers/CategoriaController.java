package org.example.proyecto_competicion.Controllers;



import org.example.proyecto_competicion.Models.Categoria;
import org.example.proyecto_competicion.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

   @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/all")
    public String getAllCategorias(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "layout/categoria_pages/categorias";
    }

    @GetMapping("/add")
    public String addCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "layout/categoria_pages/addcategoria";
    }

    @PostMapping("/add")
    public String saveCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {
        boolean exists = categoriaRepository.findByNombre(categoria.getNombre()).isPresent();
        if (exists) {
            model.addAttribute("error", "La categoría con el nombre '" + categoria.getNombre() + "' ya existe.");
            return "layout/categoria_pages/addcategoria"; // Regresa al formulario
        }
        categoriaRepository.save(categoria);
        return "Inicio";  // Redirigir a la lista de categorías
    }

}
