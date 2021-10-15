package com.egg.programacion.libreriaspring.repositories;

import com.egg.programacion.libreriaspring.entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lucía
 */

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{

}
