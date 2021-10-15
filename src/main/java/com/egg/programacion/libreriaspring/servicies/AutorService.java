package com.egg.programacion.libreriaspring.servicies;

import com.egg.programacion.libreriaspring.entities.Autor;
import com.egg.programacion.libreriaspring.exceptions.ExceptionService;
import com.egg.programacion.libreriaspring.repositories.AutorRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author Lucía
 */
@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void newAutor(String nombre) throws ExceptionService {
        validation(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(Boolean.TRUE);

        autorRepository.save(autor);
    }

    @Transactional
    public void modifyAutor(String id, String nombre) throws ExceptionService {
        validation(nombre);

        Optional<Autor> answer = autorRepository.findById(id);
        if (answer.isPresent()) {
            Autor autor = answer.get();
            autor.setNombre(nombre);

            autorRepository.save(autor);
        } else {
            throw new ExceptionService("No se encontró el autor solicitado.");
        }
    }

    @Transactional
    public void disableAutor(String id) throws ExceptionService {
        Optional<Autor> answer = autorRepository.findById(id);
        if (answer.isPresent()) {
            Autor autor = answer.get();
            autor.setAlta(Boolean.FALSE);

            autorRepository.save(autor);
        } else {
            throw new ExceptionService("No se encontró el autor solicitado.");
        }
    }

    @Transactional
    public void enableAutor(String id) throws ExceptionService {
        Optional<Autor> answer = autorRepository.findById(id);
        if (answer.isPresent()) {
            Autor autor = answer.get();
            autor.setAlta(Boolean.TRUE);

            autorRepository.save(autor);
        } else {
            throw new ExceptionService("No se encontró el autor solicitado.");
        }
    }

    public void lookUp(String id) throws ExceptionService {
        Optional<Autor> answer = autorRepository.findById(id);
        if (answer.isPresent()) {
            Autor autor = answer.get();
            System.out.println(autor.toString());
        } else {
            throw new ExceptionService("No se encontró el autor solicitado.");
        }
    }

    private void validation(String nombre) throws ExceptionService {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExceptionService("El nombre del/la autor/a no puede ser nulo.");
        }
    }

    public List<Autor> listAll() {
        return autorRepository.findAll();
    }

}
