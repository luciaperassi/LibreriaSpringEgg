package com.egg.programacion.libreriaspring;

import com.egg.programacion.libreriaspring.servicies.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibreriaspringApplication {

    
    public static void main(String[] args) {
        SpringApplication.run(LibreriaspringApplication.class, args);
    }
    

}
