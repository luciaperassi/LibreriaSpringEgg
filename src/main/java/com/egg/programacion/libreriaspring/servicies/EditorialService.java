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
    public void newEditorial(String nombre) throws ExceptionService {
        validation(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);

        editorialrepository.save(editorial);
    }

    @Transactional
    public void modifyEditorial(String id, String nombre) throws ExceptionService {
        validation(nombre);

        Optional<Editorial> answer = editorialrepository.findById(id);
        if (answer.isPresent()) {
            Editorial editorial = answer.get();
            editorial.setNombre(nombre);

            editorialrepository.save(editorial);
        } else {
            throw new ExceptionService("No se encontró la editorial solicitada.");
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

    public void lookUp(String id) throws ExceptionService {
        Optional<Editorial> answer = editorialrepository.findById(id);
        if (answer.isPresent()) {
            Editorial editorial = answer.get();
            System.out.println(editorial.toString());
        } else {
            throw new ExceptionService("No se encontró la editorial solicitada.");
        }
    }

    private void validation(String nombre) throws ExceptionService {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExceptionService("El nombre de la editorial no puede ser nulo.");
        }
    }

    public List<Editorial> listAll() {
        return editorialrepository.findAll();
    }
}
