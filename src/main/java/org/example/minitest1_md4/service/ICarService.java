package org.example.minitest1_md4.service;

import org.example.minitest1_md4.model.Car;
import org.example.minitest1_md4.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICarService extends IGenerateService<Car>{
    Iterable<Car> findAllByType(Type type);

    Page<Car> findAll(Pageable pageable);

    Page<Car> findAllByCodeContaining(Pageable pageable, String name);
}