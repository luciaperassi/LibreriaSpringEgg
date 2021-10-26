package com.egg.programacion.libreriaspring.repositories;

import com.egg.programacion.libreriaspring.entities.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lucía
 */

@Repository
public interface BookRepository extends JpaRepository<Book, String>{
    
    @Query("select b from Book b where b.titulo LIKE :q or b.autor LIKE :q or b.editorial LIKE :q")
    List<Book> findAllByQ(@Param("q") String q);
    
    @Query("select b from Book b where b.titulo = :q")
    Book findByTittle(@Param("q") String q);
    
    @Query("select b from Book b where b.isbn LIKE :q or b.autor = :q")
    Book findByIsbn(@Param("q") long q);
    
    
}
