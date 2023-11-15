package com.awsrest.awsacademyproject.Services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.awsrest.awsacademyproject.Entities.Profesor;

@Service
public class ProfesorService {
   private List<Profesor> profesores = new ArrayList<>();

   public List<Profesor> getAllProfesores() {
      return profesores;
   }

   public Profesor getProfesorById(Long id) {
      return profesores.stream().filter(profesor -> profesor.getId().equals(id)).findFirst().orElse(null);
   }

   public Profesor createProfesor(Profesor profesor) {
      profesor.setId((long) (profesores.size() + 1));
      profesores.add(profesor);
      return profesor;
   }

   public Profesor updateProfesor(Long id, Profesor profesor) {
      Profesor profesorToUpdate = getProfesorById(id);
      profesorToUpdate.setNombre(profesor.getNombre());
      profesorToUpdate.setApellido(profesor.getApellido());
      profesorToUpdate.setNumeroEmpleado(profesor.getNumeroEmpleado());
      profesorToUpdate.setHorasClase(profesor.getHorasClase());
      return profesorToUpdate;
   }

   public void deleteProfesor(Long id) {
      profesores.removeIf(profesor -> profesor.getId().equals(id));
   }
   
}
