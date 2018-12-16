package com.example.services;

import com.example.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findById(Long id);

    boolean deleteById(Long id);

    boolean add(User u);
}
