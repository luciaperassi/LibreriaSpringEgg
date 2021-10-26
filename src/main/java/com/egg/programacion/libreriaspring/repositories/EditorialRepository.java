package com.egg.programacion.libreriaspring.repositories;

import com.egg.programacion.libreriaspring.entities.Editorial;
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
public interface EditorialRepository extends JpaRepository<Editorial, String>{

    @Query("select e from Editorial e where e.nombre LIKE :q")
    List<Editorial> findAllByQ(@Param("q") String q);
    
    @Query("select e from Editorial e where e.nombre = :q")
    Editorial findByName(@Param("q") String q);
}
