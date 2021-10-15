package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.BookService;
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
@RequestMapping("/book") //Agregar try and catch con exceptions?
public class BookController {

    @Autowired
    private BookService bookservice;
    
      @GetMapping
    public String book(){
        return "book.html";
    }

    @GetMapping("/booklist")
    public String bookList(Model model) {
        model.addAttribute("books", bookservice.listAll());
        return "book-list";
    }

    //Carga la vista
    @GetMapping("/formnewbook")
    public String formNewBook() {
        return "new-book-form";
    }

    //Guarda la vista
    @PostMapping("/savenewbook")
    public String saveNewBook(@RequestParam String idAutor, @RequestParam String idEditorial, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam Boolean alta) throws ExceptionService {
        try {
            bookservice.newBook(idAutor, idEditorial, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, alta);
        } catch (ExceptionService e) {
            throw new ExceptionService("No se pudo cargar el nuevo libro.");
        }
        
        return "redirect:booklist";
    }

    @GetMapping("/formmodifybook")
    public String formModifyBook() {
        return "modify-book-form";
    }

    @PostMapping("/savemodifybook")
    public String saveModifyBook(@RequestParam String id, @RequestParam String idAutor, @RequestParam String idEditorial, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam Boolean alta) throws ExceptionService {
        bookservice.modifyBook(id, idAutor, idEditorial, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);
        return "redirect:booklist";
    }

    @GetMapping("/formdisablebook")
    public String formDisableBook() {
        return "disable-book-form";
    }
    
    @PostMapping("/disablebook")
    public String disableBook(String id) throws ExceptionService {
        bookservice.disableBook(id);
        return "redirect:booklist";
    }

    @GetMapping("/formenablebook")
    public String formEnableBook() {
        return "enable-book-form";
    }
    
    @PostMapping("/enablebook")
    public String enableBook(String id) throws ExceptionService {
        bookservice.enableBook(id);
        return "redirect:booklist";
    }
    
    @GetMapping("/lookforbook")
    public String formLookForBook(){
        return "look-for-book-form";
    }

    @PostMapping("/lookforbook")
    public String lookForBook(String id) throws ExceptionService {
        bookservice.lookForBook(id);
        return "redirect:booklist";
    }
    
    
    
}
