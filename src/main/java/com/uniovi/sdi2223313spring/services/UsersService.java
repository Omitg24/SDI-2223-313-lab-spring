package com.uniovi.sdi2223313spring.services;

import com.uniovi.sdi2223313spring.entities.Mark;
import com.uniovi.sdi2223313spring.entities.User;
import com.uniovi.sdi2223313spring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        usersRepository.save(user);
    }

    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public List<User> searchByNameAndLastName(String searchText, User user) {
        List<User> users = new LinkedList<User>();
        searchText = "%" + searchText + "%";
        users = usersRepository.searchByNameAndLastName(searchText);
        return users;
    }
}