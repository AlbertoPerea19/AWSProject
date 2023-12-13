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

    private String nombres;

    private String apellidos;

    private String matricula;

    private Double promedio;

    private String fotoPerfilUrl;

    private String password;

    public Alumno(){}

   public Alumno(String nombres, String apellidos,String matricula, Double promedio, String fotoPerfilUrl, String password) {
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.matricula = matricula;
      this.promedio = promedio;
      this.fotoPerfilUrl = fotoPerfilUrl;
      this.password = password;
   }

   public Alumno(String nombres, String apellidos, String matricula, Double promedio, String password) {
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
