package com.egg.programacion.libreriaspring.servicies;

import com.egg.programacion.libreriaspring.entities.Editorial;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
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
public class EditorialService {

    @Autowired
    private EditorialRepository editorialrepository;

    @Transactional
    public Editorial newEditorial(Editorial editorial) throws ExceptionService {
         if (editorial.getNombre().isEmpty() || editorial.getNombre() == null) {
            throw new ExceptionService("El nombre de la editorial no puede ser nulo.");
        }
        return editorialrepository.save(editorial);
    }

    @Transactional
    public Editorial newEditorial(String nombre) throws ExceptionService {
        validation(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);

        return editorialrepository.save(editorial);
    }

     @Transactional
    public void delete(Editorial editorial){
        editorialrepository.delete(editorial);
    }
    
    @Transactional
    public void deleteById(String id){
        Optional<Editorial> editorial = editorialrepository.findById(id);
        if (editorial.isPresent()) {
            editorialrepository.delete(editorial.get());
        }
        
    }

    @Transactional
    public void disableEditorial(String id) throws ExceptionService {
        Optional<Editorial> answer = editorialrepository.findById(id);
        if (answer.isPresent()) {
            Editorial editorial = answer.get();
            editorial.setAlta(Boolean.FALSE);

            editorialrepository.save(editorial);
        } else {
            throw new ExceptionService("No se encontró la editorial solicitada.");
        }
    }

    @Transactional
    public void enableAutor(String id) throws ExceptionService {
        Optional<Editorial> answer = editorialrepository.findById(id);
        if (answer.isPresent()) {
            Editorial editorial = answer.get();
            editorial.setAlta(Boolean.TRUE);

            editorialrepository.save(editorial);
        } else {
            throw new ExceptionService("No se encontró la editorial solicitada.");
        }
    }

    public Optional<Editorial> lookUp(String id) throws ExceptionService {
        return editorialrepository.findById(id);
    }

    private void validation(String nombre) throws ExceptionService {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExceptionService("El nombre de la editorial no puede ser nulo.");
        }
    }

    public Editorial findById(Editorial editorial) {
        Optional<Editorial> optional = editorialrepository.findById(editorial.getId());
            if (optional.isPresent()) {
                editorial = optional.get();
            }
        return editorial;
    }
    
    public List<Editorial> listAll() {
        return editorialrepository.findAll();
    }

    public List<Editorial> listAllByQ(String q) {
        return editorialrepository.findAllByQ("%" + q + "%");
    }
}
