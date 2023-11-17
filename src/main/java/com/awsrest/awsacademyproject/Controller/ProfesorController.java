package com.awsrest.awsacademyproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.awsrest.awsacademyproject.Entities.Profesor;
import com.awsrest.awsacademyproject.Services.ProfesorService;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {
    private ProfesorService profesorService;

    @Autowired
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> getAllProfesores() {
        List<Profesor> profesores = profesorService.getAllProfesores();
        return new ResponseEntity<>(profesores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Long id) {
        Profesor profesor = profesorService.getProfesorById(id);
        if (profesor != null) {
            return new ResponseEntity<>(profesor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor profesor) {
        if (profesor.getId() != null || profesor.getNombre() == null || profesor.getApellido() == null
                || profesor.getHorasClase() == null || profesor.getNumeroEmpleado() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Profesor createdProfesor = profesorService.createProfesor(profesor);
        return new ResponseEntity<>(createdProfesor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> updateProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        Profesor updatedProfesor = profesorService.updateProfesor(id, profesor);
        if (updatedProfesor != null) {
            return new ResponseEntity<>(updatedProfesor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        boolean deleted = profesorService.deleteProfesor(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
