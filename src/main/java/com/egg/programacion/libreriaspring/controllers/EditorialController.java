package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService editorialservice;
    
    @GetMapping
    public String editorial(){
        return "editorial.html";
    }

    @GetMapping("/editoriallist")
    public String editorialList(Model model) {
        model.addAttribute("editorials", editorialservice.listAll());
        return "editorial-list";
    }
    
    @GetMapping("/formneweditorial")
    public String formNewEditorial() {
        return "new-editorial-form";
    }
    
    @PostMapping("/saveneweditorial")
    public String saveNewEditorial(String nombre) throws ExceptionService{
        editorialservice.newEditorial(nombre);
        return "redirect:editoriallist";
    }

    @GetMapping("/formmodifyeditorial")
    public String formModifyEditorial() {
        return "modify-editorial-form";
    }

    @PostMapping("/savemodifyeditorial")
    public String saveModifyEditorial(@RequestParam String id,@RequestParam String nombre) throws ExceptionService {
        editorialservice.modifyEditorial(id, nombre);
        return "redirect:editoriallist";
    }

    @GetMapping("/formdisableeditorial")
    public String formDisableEditorial() {
        return "disable-editorial-form";
    }
    
    @PostMapping("/disableeditorial")
    public String disableEditorial(String id) throws ExceptionService {
        editorialservice.disableEditorial(id);
        return "redirect:editoriallist";
    }

    @GetMapping("/formenableeditorial")
    public String formEnableEditorial() {
        return "enable-editorial-form";
    }
    
    @PostMapping("/enableeditorial")
    public String enableEditorial(String id) throws ExceptionService {
        editorialservice.enableAutor(id);
        return "redirect:editoriallist";
    }
    
    @GetMapping("/lookforeditorial")
    public String formLookForEditorial(){
        return "look-for-editorial-form";
    }

    @PostMapping("/lookforeditorial")
    public String lookForEditorial(String id) throws ExceptionService {
        editorialservice.lookUp(id);
        return "redirect:editoriallist";
    }
}
