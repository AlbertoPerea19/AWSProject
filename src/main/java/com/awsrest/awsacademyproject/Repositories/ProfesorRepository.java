package com.awsrest.awsacademyproject.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awsrest.awsacademyproject.Entities.Profesor;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, Long> {
   
}
