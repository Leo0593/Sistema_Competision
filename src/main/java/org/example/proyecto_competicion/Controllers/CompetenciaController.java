package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.CategoriaRepository;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/competencia")
public class CompetenciaController {

    @Autowired
    private CompeticionRepository competicionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todas las competiciones
    @GetMapping("/all")
    public String getAllCompeticiones(Model model,
                                      @RequestParam(defaultValue = "asc") String order,
                                      @RequestParam(required = false) String tipo,
                                      @RequestParam(required = false) String ordenar,
                                      @RequestParam(required = false) String estado,
                                      @RequestParam(required = false) String precio) {
        List<Competicion> competiciones;

        // Filtrar por estado (abierto o cerrado)
        if (estado != null) {
            if (estado.equals("1")) {
                competiciones = competicionRepository.findByEstado(1);  // Abierto
            } else if (estado.equals("0")) {
                competiciones = competicionRepository.findByEstado(0);  // Cerrado
            } else {
                competiciones = competicionRepository.findAll();
            }
        } else {
            competiciones = competicionRepository.findAll();  // Si no se filtra por estado, mostrar todas las competiciones
        }

        // Ordenar por tipo (si se especifica)
        if (tipo != null) {
            if (tipo.equals("grupal")) {
                competiciones = competicionRepository.findByTipo("grupal");
            } else if (tipo.equals("individual")) {
                competiciones = competicionRepository.findByTipo("individual");
            }
        }

        // Ordenar por precio (si se especifica)
        if (precio != null) {
            if (order.equals("asc")) {
                competiciones = competicionRepository.findAll(Sort.by(Sort.Order.asc("precioInscripcion")));
            } else if (order.equals("desc")) {
                competiciones = competicionRepository.findAll(Sort.by(Sort.Order.desc("precioInscripcion")));
            }
        }

        // Ordenar por fecha
        if (ordenar != null && ordenar.equals("fecha")) {
            if (order.equals("desc")) {
                competiciones = competicionRepository.findAllByOrderByFechaInicioDesc();
            } else {
                competiciones = competicionRepository.findAllByOrderByFechaInicioAsc();
            }
        }


        LocalDateTime fechaActual = LocalDateTime.now();
        model.addAttribute("competiciones", competiciones);
        model.addAttribute("fechaActual", fechaActual);
        model.addAttribute("order", order);
        model.addAttribute("tipo", tipo);
        model.addAttribute("estado", estado);
        model.addAttribute("precio", precio);

        return "layout/competencia_pages/competiciones"; // Nombre de la vista
    }


    @GetMapping("/add")
    public String addCompeticion(Model model) {
        Competicion competicion = new Competicion();
        model.addAttribute("competicion", competicion);
        model.addAttribute("categorias", categoriaRepository.findAll()); // Obtener todas las categorías
        return "layout/competencia_pages/addcompeticion";
    }

    @PostMapping("/add")
    public String saveCompeticion(@ModelAttribute("competicion") Competicion competicion, Principal principal) {
        // Obtener el correo del usuario logueado desde 'principal'
        String correo = principal.getName();

        // Buscar el usuario en la base de datos por el correo electrónico
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        // Verificar si el usuario existe
        if (usuario == null) {
            return "redirect:/competencia/add?error=userNotFound";
        }

        // Asignar el ID del usuario logueado como el creador de la competición
        competicion.setIdCreador(usuario.getId());

        // Si el tipo es "individual", asignar 1 persona por grupo
        if ("individual".equals(competicion.getTipo())) {
            competicion.setPersonasPorGrupo(1);  // Si es individual, asignamos 1
        }

        // Validación de fechas: la fecha de inicio no debe ser después de la fecha de fin
        LocalDateTime fechaInicio = competicion.getFechaInicio();
        LocalDateTime fechaFin = competicion.getFechaFin();

        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            // Redirigir si las fechas no son válidas
            return "redirect:/competencia/add?error=invalidDates";
        }

        // Configurar otras propiedades de la competición
        competicion.setEstado((byte) 1);
        competicion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        competicion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Guardar la competición
        competicionRepository.save(competicion);

        // Redirigir a la página principal o la vista correspondiente
        return "redirect:/competencia/all";  // O la ruta que corresponda
    }

    @GetMapping("/edit/{id}")
    public String editCompeticion(@PathVariable("id") int id, Model model) {
        // Buscar la competición por su ID
        Competicion competicion = competicionRepository.findById(id).orElse(null);

        // Si no existe la competición, redirigir con un mensaje de error
        if (competicion == null) {
            return "redirect:/competencia/all?error=notFound";
        }

        // Agregar la competición y las categorías disponibles al modelo
        model.addAttribute("competicion", competicion);
        model.addAttribute("categorias", categoriaRepository.findAll()); // Obtener todas las categorías
        return "layout/competencia_pages/editcompeticion";
    }

    @PostMapping("/edit/{id}")
    public String updateCompeticion(@PathVariable("id") int id, @ModelAttribute("competicion") Competicion competicion) {
        // Buscar la competición por su ID
        Competicion competicionActual = competicionRepository.findById(id).orElse(null);

        // Si no existe la competición, redirigir con un mensaje de error
        if (competicionActual == null) {
            return "redirect:/competencia/all?error=notFound";
        }

        // Validación de fechas: la fecha de inicio no debe ser después de la fecha de fin
        LocalDateTime fechaInicio = competicion.getFechaInicio();
        LocalDateTime fechaFin = competicion.getFechaFin();

        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            return "redirect:/competencia/all?error=invalidDates";
        }

        // Actualizar las propiedades de la competición
        competicionActual.setNombre(competicion.getNombre());
        competicionActual.setDescripcion(competicion.getDescripcion());
        competicionActual.setFechaInicio(fechaInicio);
        competicionActual.setFechaFin(fechaFin);
        competicionActual.setIdCategoria(competicion.getIdCategoria());

        competicionActual.setTipo(competicion.getTipo());

        if ("individual".equals(competicion.getTipo())) {
            competicionActual.setPersonasPorGrupo(1);
        } else {
            competicionActual.setPersonasPorGrupo(competicion.getPersonasPorGrupo());
        }

        competicionActual.setPrecioInscripcion(competicion.getPrecioInscripcion());

        // Actualizar el ID de la categoría (suponiendo que 'idCategoria' es un campo en Competicion)
        competicionActual.setEstado(competicion.getEstado());
        competicionActual.setUbicacion(competicion.getUbicacion());

        competicionActual.setAforo(competicion.getAforo());

        competicionActual.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        // Guardar los cambios
        competicionRepository.save(competicionActual);

        // Redirigir a la página correspondiente después de la actualización
        return "redirect:/competencia/all";  // O la ruta que corresponda, puede ser la vista de la competición
    }

    @GetMapping("/delete/{id}")
    public String deleteCompeticion(@PathVariable("id") int id) {
        // Buscar la competición por su ID
        Competicion competicion = competicionRepository.findById(id).orElse(null);

        // Si no existe la competición, redirigir con un mensaje de error
        if (competicion == null) {
            return "redirect:/competencia/all?error=notFound";
        }

        // Eliminar la competición
        competicionRepository.delete(competicion);

        // Redirigir a la página principal o la vista correspondiente
        return "redirect:/competencia/all";  // O la ruta que corresponda
    }

    @GetMapping("/list")
    public String listarCompeticiones(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String ordenar,
            Model model) {

        List<Competicion> competiciones = new ArrayList<>();

        if (estado != null) {
            competiciones = competicionRepository.findByEstado(Integer.parseInt(estado));
        }

        if (tipo != null) {
            competiciones = competicionRepository.findByTipo(tipo);
        }

        if (ordenar != null && order != null) {
            if ("fecha".equals(ordenar)) {
                if ("asc".equals(order)) {
                    competiciones = competicionRepository.findAllByOrderByFechaInicioAsc();
                } else {
                    competiciones = competicionRepository.findAllByOrderByFechaInicioDesc();
                }
            } else if ("precio".equals(ordenar)) {
                // Aquí necesitarías un método en tu repositorio para ordenar por precio
                if ("asc".equals(order)) {
                    competiciones = competicionRepository.findAll(Sort.by(Sort.Order.asc("precioInscripcion")));
                } else {
                    competiciones = competicionRepository.findAll(Sort.by(Sort.Order.desc("precioInscripcion")));
                }
            }
        }

        model.addAttribute("competiciones", competiciones);
        return "competicion/list";
    }


}
