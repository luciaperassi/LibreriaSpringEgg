package com.egg.programacion.libreriaspring.repositories;

import com.egg.programacion.libreriaspring.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
 * @author Lucía
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, String> {

}