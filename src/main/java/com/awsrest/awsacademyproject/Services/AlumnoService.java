package com.awsrest.awsacademyproject.Services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.awsrest.awsacademyproject.Entities.Alumno;


@Service
public class AlumnoService {
   private List<Alumno> alumnos = new ArrayList<>();

   public List<Alumno> getAllAlumnos() {
      return alumnos;
   }

   public Alumno getAlumnoById(Long id) {
      return alumnos.stream().filter(alumno -> alumno.getId().equals(id)).findFirst().orElse(null);
   }

   public Alumno createAlumno(Alumno alumno) {
      alumno.setId((long) (alumnos.size() + 1));
      alumnos.add(alumno);
      return alumno;
   }

   public Alumno updateAlumno(Long id, Alumno alumno) {
      Alumno alumnoToUpdate = getAlumnoById(id);
      alumnoToUpdate.setNombre(alumno.getNombre());
      alumnoToUpdate.setApellido(alumno.getApellido());
      alumnoToUpdate.setMatricula(alumno.getMatricula());
      alumnoToUpdate.setPromedio(alumno.getPromedio());
      return alumnoToUpdate;
   }

   public boolean deleteAlumno(Long id) {
      return alumnos.removeIf(alumno -> alumno.getId().equals(id));
   }



}
