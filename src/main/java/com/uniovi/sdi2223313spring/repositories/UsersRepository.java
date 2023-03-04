package com.uniovi.sdi2223313spring.repositories;

import com.uniovi.sdi2223313spring.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT r FROM User r WHERE (LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.lastName) LIKE LOWER(?1))")
    Page<User> searchByNameAndLastName(Pageable pageable, String searchText);
}
