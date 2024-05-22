package org.example.minitest1_md4.repository;

import org.example.minitest1_md4.model.Car;
import org.example.minitest1_md4.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ICarRepository extends CrudRepository<Car, Long> {
    Iterable<Car> findAllByType(Type type);

    Page<Car> findAll(Pageable pageable);

    Page<Car> findAllByCodeContaining(Pageable pageable, String name);
}