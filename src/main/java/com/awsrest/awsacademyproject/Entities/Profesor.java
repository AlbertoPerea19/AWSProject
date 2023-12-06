package com.awsrest.awsacademyproject.Entities;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Profesor {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


    private String numeroEmpleado;


    private String nombres;


    private String apellidos;

    private Integer horasClase;

   public Profesor(){}

   public Profesor(String numeroEmpleado, String nombres, String apellidos, Integer horasClase) {
      this.numeroEmpleado = numeroEmpleado;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.horasClase = horasClase;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNumeroEmpleado() {
      return numeroEmpleado;
   }

   public void setNumeroEmpleado(String numeroEmpleado) {
      this.numeroEmpleado = numeroEmpleado;
   }

   public String getNombres() {
      return nombres;
   }

   public void setNombres(String nombres) {
      this.nombres = nombres;
   }

   public String getApellidos() {
      return apellidos;
   }

   public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
   }

   public Integer getHorasClase() {
      return horasClase;
   }

   public void setHorasClase(Integer horasClase) {
      this.horasClase = horasClase;
   }

    
}
