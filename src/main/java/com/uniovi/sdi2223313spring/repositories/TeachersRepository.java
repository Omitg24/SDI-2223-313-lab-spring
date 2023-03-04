package com.uniovi.sdi2223313spring.repositories;

import com.uniovi.sdi2223313spring.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TeachersRepository extends CrudRepository<Teacher, Long> {
    Teacher findByDni(String dni);

    Page<Teacher> findAll(Pageable pageable);
}
