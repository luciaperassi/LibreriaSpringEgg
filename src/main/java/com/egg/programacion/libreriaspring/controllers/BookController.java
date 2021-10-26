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
    public String formNewBook(Model model, @RequestParam(required = false) String id) {
        try {
            if (id != null) {
                Optional<Book> book = bookservice.lookForBook(id);
                if (book.isPresent()) {
                    model.addAttribute("book", book.get());
                } else {
                    return "redirect:booklist";
                }
            } else {
                model.addAttribute("book", new Book());
            }
            model.addAttribute("autors", autorservice.listAll());
            model.addAttribute("editorials", editorialservice.listAll());
        } catch (ExceptionService e) {
            model.addAttribute("error", e.getMessage());
        }

        return "new-book-form";
    }

    //Guarda la vista
    @PostMapping("/savenewbook")
    public String saveNewBook(Model model, RedirectAttributes redirectattributes, @ModelAttribute Book book) {
        try {
            bookservice.newBook(book);
            redirectattributes.addFlashAttribute("success", "Libro guardado con éxito.");
            return "redirect:booklist";
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
            return "redirect:formnewbook";
        }
        
    }

    @GetMapping("/formdisablebook")
    public String formDisableBook() {
        return "disable-book-form";
    }

    @PostMapping("/disablebook")
    public String disableBook(Model model, RedirectAttributes redirectattributes, String id) {
        if(id.isEmpty()||id==null){
            redirectattributes.addFlashAttribute("error","El id no puede ser nulo.");
            return "redirect:formdisablebook";
        }
        try {
            bookservice.disableBook(id);
            redirectattributes.addFlashAttribute("success", "Libro dado de baja con éxito.");
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:booklist";
    }

    @GetMapping("/formenablebook")
    public String formEnableBook() {
        return "enable-book-form";
    }

    @PostMapping("/enablebook")
    public String enableBook(Model model, RedirectAttributes redirectattributes, String id) {
        if(id.isEmpty()||id==null){
            redirectattributes.addFlashAttribute("error","El id no puede ser nulo.");
            return "redirect:formenablebook";
        }
        try {
            bookservice.enableBook(id);
            redirectattributes.addFlashAttribute("success", "Libro dado de alta con éxito.");
        } catch (ExceptionService e) {
            redirectattributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:booklist";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam(required = true) String id) {
        bookservice.deleteById(id);
        return "redirect:booklist";
    }

}
