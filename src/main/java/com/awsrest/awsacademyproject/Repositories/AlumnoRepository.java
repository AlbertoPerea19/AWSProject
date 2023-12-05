package com.awsrest.awsacademyproject.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awsrest.awsacademyproject.Entities.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long>{
   
}
