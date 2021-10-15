package com.egg.programacion.libreriaspring.repositories;

import com.egg.programacion.libreriaspring.entities.Book;
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
    
    @Query("select b from Book b where b.titulo = :titulo")
    public Book findBookbyTitulo (@Param("titulo") String titulo);
    
    
}
