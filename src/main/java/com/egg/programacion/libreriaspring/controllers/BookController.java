package com.egg.programacion.libreriaspring.controllers;

import com.egg.programacion.libreriaspring.entities.Book;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.servicies.AutorService;
import com.egg.programacion.libreriaspring.servicies.BookService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * @author Lucía
 */
@Controller
@RequestMapping("/book") //Agregar try and catch con exceptions?
public class BookController {

    @Autowired
    private BookService bookservice;
    
    @Autowired
    private AutorService autorservice;
    
    @Autowired
    private EditorialService editorialservice;
    

    @GetMapping
    public String book() {
        return "book.html";
    }

    @GetMapping("/booklist")
    public String bookList(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("books", bookservice.listAllByQ(q));
        } else {
            model.addAttribute("books", bookservice.listAll());
        }
        return "book-list";
    }

    //Carga la vista
    @GetMapping("/formnewbook")
    public String formNewBook(Model model, @RequestParam(required = false) String id) throws ExceptionService {
        if (id != null) {
            Optional<Book> book = bookservice.lookForBook(id);
            if (book.isPresent()) {
                model.addAttribute("book", book.get());
            } else {
                return "redirect:/booklist";
            }
        } else {
            model.addAttribute("book", new Book());
        }
        model.addAttribute("autors",autorservice.listAll());
         model.addAttribute("editorials",editorialservice.listAll());
        return "new-book-form";
    }

    //Guarda la vista
    @PostMapping("/savenewbook")
    public String saveNewBook(Model model, RedirectAttributes redirectattributes, @ModelAttribute Book book) {
        try {
            bookservice.newBook(book);
            redirectattributes.addFlashAttribute("succes","Libro guardado con éxito.");      
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error",e.getMessage());
        }
         return "redirect:booklist";
    }

    @GetMapping("/formdisablebook")
    public String formDisableBook() {
        return "disable-book-form";
    }

    @PostMapping("/disablebook")
    public String disableBook(ModelMap model, String id) throws ExceptionService {
        try {
            bookservice.disableBook(id);
            return "redirect:booklist";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "redirect:disablebook";
        }
    }

    @GetMapping("/formenablebook")
    public String formEnableBook() {
        return "enable-book-form";
    }

    @PostMapping("/enablebook")
    public String enableBook(ModelMap model, String id) throws ExceptionService {
        try {
            bookservice.enableBook(id);
            return "redirect:booklist";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "redirect:enablebook";
        }
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam(required = true) String id) {
        bookservice.deleteById(id);
        return "redirect:booklist";
    }

}
