package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.entities.Editorial;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.EditorialService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService editorialservice;

    @GetMapping
    public String editorial() {
        return "editorial.html";
    }

    @GetMapping("/editoriallist")
    public String editorialList(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("editorials", editorialservice.listAllByQ(q));
        } else {
            model.addAttribute("editorials", editorialservice.listAll());
        }
        return "editorial-list";
    }

    @GetMapping("/formneweditorial")
    public String formNewEditorial(Model model, @RequestParam(required = false) String id) {
        try {
            if (id != null) {
                Optional<Editorial> editorial = editorialservice.lookUp(id);
                if (editorial.isPresent()) {
                    model.addAttribute("editorial", editorial.get());
                } else {
                    return "redirect:/editoriallist";
                }
            } else {
                model.addAttribute("editorial", new Editorial());
            }
        } catch (ExceptionService e) {
            model.addAttribute("error", e.getMessage());
        }

        return "new-editorial-form";
    }

    @PostMapping("/saveneweditorial")
    public String saveNewEditorial(RedirectAttributes redirectattributes, Model model, @ModelAttribute Editorial editorial) {
        try {
            editorialservice.newEditorial(editorial);
            redirectattributes.addFlashAttribute("success", "Editorial guardada con éxito.");
            return "redirect:editoriallist";
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
            return "redirect:formneweditorial";
        }
        
    }

    @GetMapping("/formdisableeditorial")
    public String formDisableEditorial() {
        return "disable-editorial-form";
    }

    @PostMapping("/disableeditorial")
    public String disableEditorial(RedirectAttributes redirectattributes, Model model, String id) {
        if(id.isEmpty()||id==null){
            redirectattributes.addFlashAttribute("error","El id no puede ser nulo.");
            return "redirect:formdisableeditorial";
        }
        try {
            editorialservice.disableEditorial(id);
            redirectattributes.addFlashAttribute("success", "Editorial dada de baja con éxito.");
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:editoriallist";
    }

    @GetMapping("/formenableeditorial")
    public String formEnableEditorial() {
        return "enable-editorial-form";
    }

    @PostMapping("/enableeditorial")
    public String enableEditorial(RedirectAttributes redirectattributes, Model model, String id) {
        if(id.isEmpty()||id==null){
            redirectattributes.addFlashAttribute("error","El id no puede ser nulo.");
            return "redirect:formenableeditorial";
        }
        try {
            editorialservice.enableAutor(id);
            redirectattributes.addFlashAttribute("success", "Editorial dada de alta con éxito.");
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
            //si usas el add solo del redirectattributes muestra el error en el link
        }
        return "redirect:/editorial/editoriallist";
    }

    @GetMapping("/delete")
    public String deleteEditorial(@RequestParam(required = true) String id) {
        editorialservice.deleteById(id);
        return "redirect:editoriallist";
    }
}
