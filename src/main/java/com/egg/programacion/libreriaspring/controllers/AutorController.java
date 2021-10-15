package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @author Lucía
 */
@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorservice;
    
    @GetMapping
    public String autor(){
        return "autor.html";
    }
    
    @GetMapping("/autorlist")
    public String autorList(Model model) {
        model.addAttribute("autors", autorservice.listAll());
        return "autor-list";
    }
    
    @GetMapping("/formnewautor")
    public String formNewAutor() {
        return "new-autor-form";
    }
    
    @PostMapping("/savenewautor")
    public String saveNewAutor(String nombre) throws ExceptionService{
        autorservice.newAutor(nombre);
        return "redirect:autor/autorlist";
    }

    @GetMapping("/formmodifyautor")
    public String formModifyAutor() {
        return "modify-autor-form";
    }

    @PostMapping("/savemodifyautor")
    public String saveModifyAutor(@RequestParam String id,@RequestParam String nombre) throws ExceptionService {
      autorservice.modifyAutor(id, nombre);
        return "redirect:autor/autorlist";
    }

    @GetMapping("/formdisableautor")
    public String formDisableAutor() {
        return "disable-autor-form";
    }
    
    @PostMapping("/disableautor")
    public String disableAutor(String id) throws ExceptionService {
        autorservice.disableAutor(id);
        return "redirect:/autorlist";
    }

    @GetMapping("/formdenableautor")
    public String formEnableAutor() {
        return "enable-autor-form";
    }
    
    @PostMapping("/enableautor")
    public String enableAutor(String id) throws ExceptionService {
        autorservice.enableAutor(id);
        return "redirect:autor/autorlist";
    }
    
    @GetMapping("/lookforautor")
    public String formLookForAutor(){
        return "look-for-autor-form";
    }

    @PostMapping("/lookforautor")
    public String lookForAutor(String id) throws ExceptionService {
        autorservice.lookUp(id);
        return "redirect:autor/autorlist";
    }
}
