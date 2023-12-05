package com.awsrest.awsacademyproject.Services;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      return profesorRepository.save(profesor);
   }

   public Profesor updateProfesor(Long id, Profesor profesor) {
      Optional<Profesor> profesorUpdate = profesorRepository.findById(id);
      if (profesorUpdate.isPresent()) {
         Profesor profesorUpdated = profesorUpdate.get();
         profesorUpdated.setNombre(profesor.getNombre());
         profesorUpdated.setApellido(profesor.getApellido());
         profesorUpdated.setHorasClase(profesor.getHorasClase());
         profesorUpdated.setNumeroEmpleado(profesor.getNumeroEmpleado());
         return profesorRepository.save(profesorUpdated);
      } else {
         return null;
      }
   }

   public boolean deleteProfesor(Long id) {
      try {
         profesorRepository.deleteById(id);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
   
}
