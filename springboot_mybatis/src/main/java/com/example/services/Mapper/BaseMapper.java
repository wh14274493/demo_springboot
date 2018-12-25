package com.example.services.Mapper;

import java.util.List;

public interface BaseMapper<T>{

    T findById(Long id);
    List<T> findAll();
    Boolean deleteById(Long id);
    Boolean insert(T t);
}
