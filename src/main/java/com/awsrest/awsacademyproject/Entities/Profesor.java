package com.awsrest.awsacademyproject.Entities;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Profesor {
   private Long id;

    @NotNull
    @Pattern(regexp = "\\d{6}") // Número de empleado con 6 dígitos
    private String numeroEmpleado;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    private String apellido;

    @NotNull
    private Integer horasClase;

   

   public Profesor(Long id, @NotNull @Pattern(regexp = "\\d{6}") String numeroEmpleado,
         @NotBlank @Size(max = 50) String nombre, @NotBlank @Size(max = 50) String apellido,
         @NotNull Integer horasClase) {
      this.id = id;
      this.numeroEmpleado = numeroEmpleado;
      this.nombre = nombre;
      this.apellido = apellido;
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

   public Integer getHorasClase() {
      return horasClase;
   }

   public void setHorasClase(Integer horasClase) {
      this.horasClase = horasClase;
   }

    
}
