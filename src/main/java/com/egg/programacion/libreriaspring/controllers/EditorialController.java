package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.entities.Editorial;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.EditorialService;
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
    public String formNewEditorial(Model model, @RequestParam(required=false) String id) throws ExceptionService {
        if(id!=null){
            Optional<Editorial> editorial = editorialservice.lookUp(id);
            if(editorial.isPresent()){
                model.addAttribute("editorial",editorial.get());
            }else{
                return "redirect:/editoriallist";
            }
        }else{
            model.addAttribute("editorial",new Editorial());
        }
        return "new-editorial-form";
    }

    @PostMapping("/saveneweditorial")
    public String saveNewEditorial(ModelMap model,@ModelAttribute Editorial editorial) throws ExceptionService {
        try {
            editorialservice.newEditorial(editorial);
            return "redirect:editoriallist";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "redirect:formneweditorial";
        }
    }

    @GetMapping("/formdisableeditorial")
    public String formDisableEditorial() {
        return "disable-editorial-form";
    }

    @PostMapping("/disableeditorial")
    public String disableEditorial(ModelMap model,String id) throws ExceptionService {
         try {
            editorialservice.disableEditorial(id);
            return "redirect:editoriallist";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "redirect:disableeditorial";
        }
    }

    @GetMapping("/formenableeditorial")
    public String formEnableEditorial() {
        return "enable-editorial-form";
    }

    @PostMapping("/enableeditorial")
    public String enableEditorial(ModelMap model,String id) throws ExceptionService {
        try {
            editorialservice.enableAutor(id);
            return "redirect:editoriallist";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "redirect:enableeditorial";
        }
    }

     @GetMapping("/delete")
    public String deleteEditorial(@RequestParam(required=true) String id){
        editorialservice.deleteById(id);
        return "redirect:editoriallist";
    }
}
