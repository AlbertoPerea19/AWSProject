package com.awsrest.awsacademyproject.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.awsrest.awsacademyproject.Entities.Alumno;
import com.awsrest.awsacademyproject.Services.AlumnoService;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    private AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseEntity<List<Alumno>> getAllAlumnos() {
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Long id) {
        Optional<Alumno> alumnoOptional = alumnoService.getAlumnoById(id);
        if (alumnoOptional.isPresent()) {
            Alumno alumno = alumnoOptional.get();
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@RequestBody Alumno alumno) {
        Alumno createdAlumno = alumnoService.createAlumno(alumno);
        return new ResponseEntity<>(createdAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        Alumno updatedAlumno = alumnoService.updateAlumno(id, alumno);
        if (updatedAlumno != null) {
            return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {

        boolean deleted = alumnoService.deleteAlumno(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/fotoPerfil")
    public ResponseEntity<Map<String, String>> uploadFotoPerfil(@PathVariable Long id, @RequestParam("foto") MultipartFile file) {
        String fotoPerfilUrl = alumnoService.uploadFotoPerfil(id, file);
        Map<String, String> response = new HashMap<>();
        response.put("fotoPerfilUrl", fotoPerfilUrl);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PostMapping(value = "/{id}/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendNotificationSNS(@PathVariable Long id) {
        alumnoService.sendNotificationSNS(id);
        return ResponseEntity.ok("Notificación enviada con éxito");
    }

    @PostMapping("/{id}/session/login")
    public ResponseEntity<Map<String, String>> login(@PathVariable Long id, @RequestBody Alumno alumno) {
        String session = alumnoService.login(id, alumno.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("sessionString", session);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @PostMapping("/{id}/session/verify")
    public ResponseEntity<Map<String, Boolean>> verifySession(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String sessionString = requestBody.get("sessionString");
        Map<String, Boolean> response = new HashMap<>();

        if (sessionString != null) {
            boolean valid = alumnoService.verifySession(id, sessionString);
            response.put("isValid", valid);

            if (valid) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(response);
            }
        } else {
            response.put("isValid", false);
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }

    
    @PostMapping("/{id}/session/logout")
    public ResponseEntity<Map<String, String>> logout(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String sessionString = requestBody.get("sessionString");
        Map<String, String> response = new HashMap<>();

        if (sessionString != null) {
            alumnoService.logout(id, sessionString);
            response.put("message", "Sesión cerrada con éxito");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } else {
            response.put("error", "No se proporcionó una sesión");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }
}
