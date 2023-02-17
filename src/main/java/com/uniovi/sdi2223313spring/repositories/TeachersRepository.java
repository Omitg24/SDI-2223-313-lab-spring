package com.uniovi.sdi2223313spring.repositories;

import com.uniovi.sdi2223313spring.entities.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeachersRepository extends CrudRepository<Teacher, Long> {
}
