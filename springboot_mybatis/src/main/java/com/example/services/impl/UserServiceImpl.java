package com.example.services.impl;

import com.example.domain.User;
import com.example.services.Mapper.UserMapper;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper UserMapper;

    @Override
    public List<User> findAll() {

        return UserMapper.findAll();
    }

    @Override
    public User findById(Long id) {
        return UserMapper.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return UserMapper.deleteById(id);
    }

    @Override
    public boolean add(User u) {
        return UserMapper.insert(u);
    }
}
