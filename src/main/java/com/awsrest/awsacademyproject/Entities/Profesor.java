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

    @NotNull
    @Pattern(regexp = "\\d{6}") // Número de empleado con 6 dígitos
    private String numeroEmpleado;

    @NotBlank
    @Size(max = 50)
    private String nombres;

    @NotBlank
    @Size(max = 50)
    private String apellidos;

    @NotNull
    private Integer horasClase;

   public Profesor(){}

   public Profesor(Long id, @NotNull @Pattern(regexp = "\\d{6}") String numeroEmpleado,
         @NotBlank @Size(max = 50) String nombres, @NotBlank @Size(max = 50) String apellidos,
         @NotNull Integer horasClase) {
      this.id = id;
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

   public String getNombre() {
      return nombres;
   }

   public void setNombre(String nombres) {
      this.nombres = nombres;
   }

   public String getApellido() {
      return apellidos;
   }

   public void setApellido(String apellidos) {
      this.apellidos = apellidos;
   }

   public Integer getHorasClase() {
      return horasClase;
   }

   public void setHorasClase(Integer horasClase) {
      this.horasClase = horasClase;
   }

    
}
