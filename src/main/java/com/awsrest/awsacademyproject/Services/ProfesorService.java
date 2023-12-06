package com.awsrest.awsacademyproject.Services;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.awsrest.awsacademyproject.Entities.Alumno;
import com.awsrest.awsacademyproject.Entities.Profesor;
import com.awsrest.awsacademyproject.Repositories.ProfesorRepository;

@Service
public class ProfesorService {
   
   @Autowired
   private ProfesorRepository profesorRepository;

   public List<Profesor> getAllProfesores() {
      return (List<Profesor>) profesorRepository.findAll();
   }

   public Optional<Profesor> getProfesorById(Long id) {
      return profesorRepository.findById(id);
   }

   public Profesor createProfesor(Profesor profesor) {
      if (profesor.getNombres() == null || profesor.getApellidos() == null || profesor.getNumeroEmpleado() == null || profesor.getHorasClase() == null) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos de profesor son inválidos");
      }
      return profesorRepository.save(profesor);
   }

   public Profesor updateProfesor(Long id, Profesor profesor) {
      if (profesor.getNombres() == null || profesor.getApellidos() == null || profesor.getNumeroEmpleado() == null || profesor.getHorasClase() == null) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos de profesor son inválidos");
      }
      Optional<Profesor> profesorUpdate = profesorRepository.findById(id);
      if (profesorUpdate.isPresent()) {
         Profesor profesorUpdated = profesorUpdate.get();
         profesorUpdated.setNombres(profesor.getNombres());
         profesorUpdated.setApellidos(profesor.getApellidos());
         profesorUpdated.setHorasClase(profesor.getHorasClase());
         profesorUpdated.setNumeroEmpleado(profesor.getNumeroEmpleado());
         return profesorRepository.save(profesorUpdated);
      } else {
         return null;
      }
   }

   public boolean deleteProfesor(Long id) {
      Optional<Profesor> optionalProfesor = profesorRepository.findById(id);
      if (optionalProfesor.isPresent()) {
         Profesor profesor = optionalProfesor.get();
         profesorRepository.delete(profesor);
         return true;
      } else {
         return false;
      }
   }
   
}
