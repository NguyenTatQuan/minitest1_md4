package org.example.minitest1_md4.service.impl;

import org.example.minitest1_md4.model.Car;
import org.example.minitest1_md4.model.Type;
import org.example.minitest1_md4.repository.ICarRepository;
import org.example.minitest1_md4.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService implements ICarService {
    @Autowired
    private ICarRepository iCarRepository;

    @Override
    public Iterable<Car> findAll() {
        return iCarRepository.findAll();
    }

    @Override
    public void save(Car car) {
        iCarRepository.save(car);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return iCarRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iCarRepository.deleteById(id);
    }

    @Override
    public Iterable<Car> findAllByType(Type type) {
        return iCarRepository.findAllByType(type);
    }

    @Override
    public Page<Car> findAll(Pageable pageable) {
        return iCarRepository.findAll(pageable);
    }

    @Override
    public Page<Car> findAllByCodeContaining(Pageable pageable, String code) {
        return iCarRepository.findAllByCodeContaining(pageable, code);
    }
}