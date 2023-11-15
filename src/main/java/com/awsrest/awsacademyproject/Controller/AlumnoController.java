package com.awsrest.awsacademyproject.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   public List<Alumno> getAllAlumnos() {
      return alumnoService.getAllAlumnos();
   }

   @GetMapping("/{id}")
   public Alumno getAlumnoById(@PathVariable Long id) {
      return alumnoService.getAlumnoById(id);
   }

   @PostMapping
   public Alumno createAlumno(@RequestBody Alumno alumno) {
      return alumnoService.createAlumno(alumno);
   }

   @PutMapping("/{id}")
   public Alumno updateAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
      return alumnoService.updateAlumno(id,alumno);
   }

   @DeleteMapping("/{id}")
   public void deleteAlumno(@PathVariable Long id) {
      alumnoService.deleteAlumno(id);
   }


   
}
