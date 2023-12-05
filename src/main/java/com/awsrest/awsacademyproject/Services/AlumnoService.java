package com.awsrest.awsacademyproject.Services;



import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.awsrest.awsacademyproject.Entities.Alumno;
import com.awsrest.awsacademyproject.Repositories.AlumnoRepository;

import jakarta.persistence.EntityNotFoundException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.ResourceNotFoundException;
import software.amazon.awssdk.services.sns.model.SnsException;


@Service
public class AlumnoService {

   @Autowired
   private AlumnoRepository alumnoRepository;

   @Autowired
   private S3Client s3Client;

   @Value("${aws.sns.topic}")
   private String topicArn;

   private final SnsClient snsClient;

   private final DynamoDbClient dynamoDbClient;



   public AlumnoService(SnsClient snsClient, DynamoDbClient dynamoDbClient) {
      this.snsClient = snsClient;
      this.dynamoDbClient = dynamoDbClient;
   }

   public List<Alumno> getAllAlumnos() {
      return (List<Alumno>) alumnoRepository.findAll();      
   }

   public Optional<Alumno> getAlumnoById(Long id) {
      return alumnoRepository.findById(id);
   }

   public Alumno createAlumno(Alumno alumno) {
      return alumnoRepository.save(alumno);
   }

   public Alumno updateAlumno(Long id, Alumno alumno) {
      Optional<Alumno> alumnoupdate= alumnoRepository.findById(id);
      if (alumnoupdate.isPresent()) {
         Alumno alumnoUpdated = alumnoupdate.get();
         alumnoUpdated.setNombre(alumno.getNombre());
         alumnoUpdated.setApellido(alumno.getApellido());
         alumnoUpdated.setMatricula(alumno.getMatricula());
         alumnoUpdated.setPromedio(alumno.getPromedio());
         alumnoUpdated.setFotoPerfilUrl(alumno.getFotoPerfilUrl());
         alumnoUpdated.setPassword(alumno.getPassword());
         return alumnoRepository.save(alumnoUpdated);
      } else {
         return null;
      }
      
   }

   public boolean deleteAlumno(Long id) {
      Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
      if (optionalAlumno.isPresent()) {
         Alumno alumno = optionalAlumno.get();
         alumnoRepository.delete(alumno);
         return true;
      } else {
         return false;
      }
   }

   public String uploadFotoPerfil(Long id, MultipartFile file) {
      try {
          // Lógica para subir la foto a S3 usando S3Client
          String key = id + ".jpg";
          PutObjectResponse response = s3Client.putObject(PutObjectRequest.builder()
                  .bucket("awsacademy.mx")
                  .key(key)
                  .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            if (response.sdkHttpResponse().isSuccessful()) {
               // Obtiene la URL del objeto en S3
               String fotoPerfilUrl = s3Client.utilities().getUrl(GetUrlRequest.builder()
                        .bucket("awsacademy.mx")
                        .key(key)
                        .build())
                        .toExternalForm();

               // Guardar la URL en la base de datos
               Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
               if (optionalAlumno.isPresent()) {
                  Alumno alumno = optionalAlumno.get();
                  alumno.setFotoPerfilUrl(fotoPerfilUrl);
                  alumnoRepository.save(alumno);
               } else {
                  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + id);
               }

               // Retorna la URL de la foto de perfil en S3
               return fotoPerfilUrl;
            } else {
               throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al subir la foto de perfil a S3");
            }
          
      } catch (IOException e) {
          // Manejar la excepción según tus necesidades
          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al subir la foto de perfil a S3", e);
      }
   }

   public void sendNotificationSNS(Long id) {
      Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);

    if (optionalAlumno.isPresent()) {
        Alumno alumno = optionalAlumno.get();
        
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(alumno.getNombre() + "\n " + alumno.getApellido() + " \n" + alumno.getPromedio())
                    .topicArn(topicArn)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            // Manejar las excepciones específicas de SNS si es necesario
            System.err.println(e.awsErrorDetails().errorMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar notificación SNS", e);
        }
    } else {
        // Manejar el caso en el que no se encuentra el alumno
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + id);
    }
   }

   public String login(Long id, String password) {
      Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
      if (optionalAlumno.isPresent()) {
         Alumno alumno = optionalAlumno.get();
         if (alumno.getPassword().equals(password)) {
            String sessionString = generarSessionString();
            long fecha = Instant.now().getEpochSecond();

            Map<String, AttributeValue> itemValues = new HashMap<>();
            itemValues.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
            itemValues.put("fecha", AttributeValue.builder().n(String.valueOf(fecha)).build());
            itemValues.put("alumnoId", AttributeValue.builder().n(String.valueOf(alumno.getId())).build());
            itemValues.put("active", AttributeValue.builder().bool(true).build());
            itemValues.put("sessionString", AttributeValue.builder().s(sessionString).build());

            escribirSesionEnDynamoDB(itemValues);

            return sessionString;
         }else{
            System.out.println("Contraseña incorrecta");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña incorrecta");
         }
      } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + id);
      }
   }

   private String generarSessionString() {
      StringBuilder sessionStringBuilder = new StringBuilder();
      for (int i = 0; i < 128; i++) {
         int randomDigit = (int) (Math.random() * 10);
         sessionStringBuilder.append(randomDigit);
      }
      return sessionStringBuilder.toString();
   }

   private void escribirSesionEnDynamoDB(Map<String, AttributeValue> itemValues) {
        try {
            
            PutItemRequest putItemRequest = PutItemRequest.builder()
                    .tableName("sesiones-alumnos") 
                    .item(itemValues)
                    .build();

            dynamoDbClient.putItem(putItemRequest);

        } catch (DynamoDbException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al escribir sesión en DynamoDB", e);
        }catch (ResourceNotFoundException e) {
            System.err.format("Error: The Amazon DynamoDB table \"%s\" can't be found.\n", "sesiones-alumnos");
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al escribir sesión en DynamoDB", e);        } 
    }

   public boolean verifySession(Long id, String sessionString) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if (optionalAlumno.isPresent()) {
            Alumno alumno = optionalAlumno.get();
            try {
            ScanRequest scanRequest = ScanRequest.builder()
                .tableName("sesiones-alumnos")
                .build();

            ScanResponse response = dynamoDbClient.scan(scanRequest);
            for (Map<String, AttributeValue> item : response.items()) {   
               if (item.get("alumnoId").n().equals(String.valueOf(alumno.getId())) &&
                        item.get("sessionString").s().equals(sessionString) &&
                        item.get("active").bool()) {
                    return true;
                }
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al escribir sesión en DynamoDB", e);        
         }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + id);
        }
         return false;
      
   }

   public void logout(Long id, String sessionString) {
      Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
      if (optionalAlumno.isPresent()) {
         Alumno alumno = optionalAlumno.get();
         try {
            ScanRequest scanRequest = ScanRequest.builder()
                .tableName("sesiones-alumnos")
                .build();

            ScanResponse response = dynamoDbClient.scan(scanRequest);
            for (Map<String, AttributeValue> item : response.items()) {   
               if (item.get("alumnoId").n().equals(String.valueOf(alumno.getId())) &&
                        item.get("sessionString").s().equals(sessionString) &&
                        item.get("active").bool()) {
                    Map<String, AttributeValue> itemValues = new HashMap<>();
                    itemValues.put("id", AttributeValue.builder().s(item.get("id").s()).build());
                    itemValues.put("fecha", AttributeValue.builder().n(item.get("fecha").n()).build());
                    itemValues.put("alumnoId", AttributeValue.builder().n(item.get("alumnoId").n()).build());
                    itemValues.put("active", AttributeValue.builder().bool(false).build());
                    itemValues.put("sessionString", AttributeValue.builder().s(item.get("sessionString").s()).build());

                    escribirSesionEnDynamoDB(itemValues);
                }
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al escribir sesión en DynamoDB", e);        
         }
      } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado con id: " + id);
      }
   }



}
