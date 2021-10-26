package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.entities.Autor;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.AutorService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * @author Lucía
 */
@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorservice;

    @GetMapping
    public String autor() {
        return "autor.html";
    }

    @GetMapping("/autorlist")
    public String autorList(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("autors", autorservice.listAllByQ(q));
        } else {
            model.addAttribute("autors", autorservice.listAll());
        }
        return "autor-list";
    }

    @GetMapping("/formnewautor")
    public String formNewAutor(Model model, @RequestParam(required = false) String id) {
        try {
            if (id != null) {
                Optional<Autor> autor = autorservice.lookUp(id);
                if (autor.isPresent()) {
                    model.addAttribute("autor", autor.get());
                } else {
                    return "redirect:/autorlist";
                }
            } else {
                model.addAttribute("autor", new Autor());
            }
        } catch (ExceptionService e) {
                model.addAttribute("error",e.getMessage());
        }

        return "new-autor-form";
    }

    @PostMapping("/savenewautor")
    public String saveNewAutor(RedirectAttributes redirectattributes, Model model, @ModelAttribute Autor autor) {
        try {
            autorservice.newAutor(autor);
            redirectattributes.addFlashAttribute("success", "Autor/a guardado/a con éxito.");
            return "redirect:/autor/autorlist";
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
            return "redirect:formnewautor";
        }
        
    }

    @GetMapping("/formdisableautor")
    public String formDisableAutor() {
        return "disable-autor-form";
    }

    @PostMapping("/disableautor")
    public String disableAutor(RedirectAttributes redirectattributes, Model model, @RequestParam String id) {
        if(id.isEmpty()||id==null){
            redirectattributes.addFlashAttribute("error","El id no puede ser nulo.");
            return "redirect:formdisableautor";
        }
        try {
            autorservice.disableAutor(id);
            redirectattributes.addFlashAttribute("success", "Autor/a dado/a de baja con éxito.");
            return "redirect:autorlist";
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
            return "redirect:formdisableautor";
        }
        
    }

    @GetMapping("/formdenableautor")
    public String formEnableAutor() {
        return "enable-autor-form";
    }

    @PostMapping("/enableautor")
    public String enableAutor(RedirectAttributes redirectattributes, Model model, @RequestParam String id) {
        if(id.isEmpty()||id==null){
            redirectattributes.addFlashAttribute("error","El id no puede ser nulo.");
            return "redirect:formdisableautor";
        }
        try {
            autorservice.enableAutor(id);
           redirectattributes.addFlashAttribute("success", "Autor/a dado/a de alta con éxito.");
            return "redirect:autorlist";
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
            return "redirect:formdenableautor";
        }
        
    }

    @GetMapping("/delete")
    public String deleteAutor(@RequestParam(required = true) String id) {
        autorservice.deleteById(id);
        return "redirect:autorlist";
    }

}
