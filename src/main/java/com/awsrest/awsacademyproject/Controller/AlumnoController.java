package com.awsrest.awsacademyproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.awsrest.awsacademyproject.Entities.Alumno;
import com.awsrest.awsacademyproject.Services.AlumnoService;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    private AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseEntity<List<Alumno>> getAllAlumnos() {
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Long id) {
        Alumno alumno = alumnoService.getAlumnoById(id);
        if (alumno != null) {
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@RequestBody Alumno alumno) {
        if (alumno.getId() != null || alumno.getNombre() == null || alumno.getApellido() == null
                || alumno.getMatricula() == null || alumno.getPromedio() == null) {
            // If the ID is provided in the request, it's a bad request
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Alumno createdAlumno = alumnoService.createAlumno(alumno);
        return new ResponseEntity<>(createdAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        Alumno updatedAlumno = alumnoService.updateAlumno(id, alumno);
        if (updatedAlumno != null) {
            return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        boolean deleted = alumnoService.deleteAlumno(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
