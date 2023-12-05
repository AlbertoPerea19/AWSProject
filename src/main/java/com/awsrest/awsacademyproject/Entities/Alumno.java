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
public class Alumno {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @NotBlank
    @Size(max = 50)
    private String nombres;

    @NotBlank
    @Size(max = 50)
    private String apellidos;

    @NotNull
    @Pattern(regexp = "\\d{6}") // Matrícula con 6 dígitos
    private String matricula;

    @NotNull
    private Double promedio;

    private String fotoPerfilUrl;

    @Size(max = 30)
    private String password;

    public Alumno(){}

   public Alumno(Long id, @NotBlank @Size(max = 50) String nombres, @NotBlank @Size(max = 50) String apellidos,
         @NotNull @Pattern(regexp = "\\d{6}") String matricula, @NotNull Double promedio, String fotoPerfilUrl,
         @Size(max = 30) String password) {
      this.id = id;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.matricula = matricula;
      this.promedio = promedio;
      this.fotoPerfilUrl = fotoPerfilUrl;
      this.password = password;
   }

   public Alumno(Long id, @NotBlank @Size(max = 50) String nombres, @NotBlank @Size(max = 50) String apellidos,
         @NotNull @Pattern(regexp = "\\d{6}") String matricula, @NotNull Double promedio,
         @Size(max = 30) String password) {
      this.id = id;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.matricula = matricula;
      this.promedio = promedio;
      this.password = password;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
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

   public String getFotoPerfilUrl() {
      return fotoPerfilUrl;
   }

   public void setFotoPerfilUrl(String fotoPerfilUrl) {
      this.fotoPerfilUrl = fotoPerfilUrl;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

    
}
