package com.awsrest.awsacademyproject.Entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Alumno {
   private Long id;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    private String apellido;

    @NotNull
    @Pattern(regexp = "\\d{6}") // Matrícula con 6 dígitos
    private String matricula;

    @NotNull
    private Double promedio;

    

   public Alumno(Long id, @NotBlank @Size(max = 50) String nombre, @NotBlank @Size(max = 50) String apellido,
         @NotNull @Pattern(regexp = "\\d{6}") String matricula, @NotNull Double promedio) {
      this.id = id;
      this.nombre = nombre;
      this.apellido = apellido;
      this.matricula = matricula;
      this.promedio = promedio;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getApellido() {
      return apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   public String getMatricula() {
      return matricula;
   }

   public void setMatricula(String matricula) {
      this.matricula = matricula;
   }

   public Double getPromedio() {
      return promedio;
   }

   public void setPromedio(Double promedio) {
      this.promedio = promedio;
   }

    
}
