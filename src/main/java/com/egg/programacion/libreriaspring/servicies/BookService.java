package com.egg.programacion.libreriaspring.servicies;

import com.egg.programacion.libreriaspring.entities.Autor;
import com.egg.programacion.libreriaspring.entities.Book;
import com.egg.programacion.libreriaspring.entities.Editorial;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.repositories.BookRepository;
import com.egg.programacion.libreriaspring.repositories.AutorRepository;
import com.egg.programacion.libreriaspring.repositories.EditorialRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author Lucía
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookrepository;
    @Autowired
    private AutorRepository autorrepository;
    @Autowired
    private EditorialRepository editorialrepository;
    @Autowired
    private AutorService autorservice;
    @Autowired
    private EditorialService editorialservice;

    @Transactional
    public Book newBook(Book book)  throws ExceptionService{
        if (book.getTitulo() == null || book.getTitulo().isEmpty()) {
            throw new ExceptionService("El titulo del libro no puede ser nulo.");
        }
        
        if (book.getIsbn() == null) {
            throw new ExceptionService("El isbn del libro no puede ser nulo.");
        }

        if (book.getEjemplaresPrestados() > book.getEjemplares()) {
            throw new ExceptionService("La cantidad de ejemplares prestados no puede superar la cantidad de ejemplares totales");
        }

        if (book.getEjemplaresRestantes() > book.getEjemplares()) {
            throw new ExceptionService("La cantidad de ejemplares restantes no puede superar la cantidad de ejemplares totales");
        }

        if (book.getEjemplaresPrestados() + book.getEjemplaresRestantes() > book.getEjemplares()) {
            throw new ExceptionService("La cantidad de ejemplares restantes + prestados no puede superar la cantidad de ejemplares totales");
        }
        if(findByTittle(book.getTitulo()) != null){ //validacion de ingresos unicos de libros controlando los nombres
            throw new ExceptionService("El título ingresado ya se encuentra registrado en la base de datos.");
        }
        
        if(findByIsbn(book.getIsbn()) != null){ //validacion de ingresos unicos de libros controlando los isbn
            throw new ExceptionService("El isbn ingresado ya se encuentra registrado en la base de datos.");
        }

        if (book.getAutor() == null) {
            throw new ExceptionService("El/la autor/a del libro no puede ser nulo/a.");
        }else{
            book.setAutor(autorservice.findById(book.getAutor()));
        }
        

        if (book.getEditorial() == null) {
            throw new ExceptionService("La editorial del libro no puede ser nula.");
        }else{
            book.setEditorial(editorialservice.findById(book.getEditorial()));
        }
         return bookrepository.save(book);
    }
    
    @Transactional
    public Book newBook(String idAutor, String idEditorial, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta) throws ExceptionService {
        validation(idAutor, idEditorial, titulo, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Book book = new Book();
        book.setAlta(alta);
        book.setAnio(anio);

        Autor autor = autorrepository.findById(idAutor).get();
        if (autor == null) {
            throw new ExceptionService("El id ingresado no corresponde a un autor cargado en la base de datos.");
        } else {
            book.setAutor(autor);
        }

        Editorial editorial = editorialrepository.findById(idEditorial).get();
        if (editorial == null) {
            throw new ExceptionService("El id ingresado no corresponde a una editorial cargada en la base de datos.");
        } else {
            book.setEditorial(editorial);
        }

        book.setEjemplares(ejemplares);
        book.setEjemplaresPrestados(ejemplaresPrestados);
        book.setEjemplaresRestantes(ejemplaresRestantes);
        book.setIsbn(isbn);
        book.setTitulo(titulo);

        return bookrepository.save(book);
    }
    
    @Transactional
    public void delete(Book book) {
        bookrepository.delete(book);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Book> book = bookrepository.findById(id);
        if (book.isPresent()) {
            bookrepository.delete(book.get());
        }
    }

    @Transactional
    public void modifyBook(String id, String idAutor, String idEditorial, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws ExceptionService {
        validation(idAutor, idEditorial, titulo, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Optional<Book> answer = bookrepository.findById(id);

        if (answer.isPresent()) {
            Book book = answer.get();
            book.setAnio(anio);
            Autor autor = autorrepository.findById(idAutor).get();
            if (autor == null) {
                throw new ExceptionService("El id ingresado no corresponde a un autor cargado en la base de datos.");
            } else {
                book.setAutor(autor);
            }

            Editorial editorial = editorialrepository.findById(idEditorial).get();
            if (editorial == null) {
                throw new ExceptionService("El id ingresado no corresponde a una editorial cargada en la base de datos.");
            } else {
                book.setEditorial(editorial);
            }
            book.setEjemplares(ejemplares);
            book.setEjemplaresPrestados(ejemplaresPrestados);
            book.setEjemplaresRestantes(ejemplaresRestantes);
            book.setIsbn(isbn);
            book.setTitulo(titulo);

            bookrepository.save(book);
        } else {
            throw new ExceptionService("No se encontró el libro solicitado");
        }
    }

    @Transactional
    public void disableBook(String id) throws ExceptionService {
        Optional<Book> answer = bookrepository.findById(id);
        if (answer.isPresent()) {
            Book book = answer.get();
            book.setAlta(Boolean.FALSE);

            bookrepository.save(book);
        } else {
            throw new ExceptionService("No se encontró el libro solicitado");
        }
    }

    @Transactional
    public void enableBook(String id) throws ExceptionService {
        Optional<Book> answer = bookrepository.findById(id);
        if (answer.isPresent()) {
            Book book = answer.get();
            book.setAlta(Boolean.TRUE);

            bookrepository.save(book);
        } else {
            throw new ExceptionService("No se encontró el libro solicitado");
        }
    }

    public Optional<Book> lookForBook(String id) throws ExceptionService {
        return bookrepository.findById(id);
    }
    
    public Book findByIsbn(Long isbn) throws ExceptionService {
        return bookrepository.findByIsbn(isbn);
    }
    
    public Book findByTittle(String tittle) throws ExceptionService {
        return bookrepository.findByTittle(tittle);
    }

    public List<Book> listAll() {
        return bookrepository.findAll();
    }

     public List<Book> listAllByQ(String q) {
        return bookrepository.findAllByQ("%"+q+"%");
    }
     
    private void validation(String autor, String editorial, String titulo, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws ExceptionService {
        if (titulo == null || titulo.isEmpty()) {
            throw new ExceptionService("El titulo del libro no puede ser nulo.");
        }

        if (ejemplaresPrestados > ejemplares) {
            throw new ExceptionService("La cantidad de ejemplares prestados no puede superar la cantidad de ejemplares totales");
        }

        if (ejemplaresRestantes > ejemplares) {
            throw new ExceptionService("La cantidad de ejemplares restantes no puede superar la cantidad de ejemplares totales");
        }

        if (ejemplaresRestantes + ejemplaresPrestados > ejemplares) {
            throw new ExceptionService("La cantidad de ejemplares restantes + prestados no puede superar la cantidad de ejemplares totales");
        }

        if (autor == null) {
            throw new ExceptionService("El/la autor/a del libro no puede ser nulo/a.");
        }

        if (editorial == null) {
            throw new ExceptionService("La editorial del libro no puede ser nula.");
        }
    }

}
