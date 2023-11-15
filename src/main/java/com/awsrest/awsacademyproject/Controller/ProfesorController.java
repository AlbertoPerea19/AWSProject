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
   public List<Profesor> getAllProfesores() {
      return profesorService.getAllProfesores();
   }

   @GetMapping("/{id}")
   public Profesor getProfesorById(@PathVariable Long id) {
      return profesorService.getProfesorById(id);
   }

   @PostMapping
   public Profesor createProfesor(@RequestBody Profesor profesor) {
      return profesorService.createProfesor(profesor);
   }

   @PutMapping("/{id}")
   public Profesor updateProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
      return profesorService.updateProfesor(id,profesor);
   }

   @DeleteMapping("/{id}")
   public void deleteProfesor(@PathVariable Long id) {
      profesorService.deleteProfesor(id);
   }
   
}
