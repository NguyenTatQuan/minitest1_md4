package org.example.minitest1_md4.service;

import org.example.minitest1_md4.exception.DuplicateCodeException;

import java.util.Optional;

public interface IGenerateService<T> {
    Iterable<T> findAll();

    void save(T t) throws DuplicateCodeException;

    Optional<T> findById(Long id);

    void remove(Long id);
}