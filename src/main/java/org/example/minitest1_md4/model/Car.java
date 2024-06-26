package org.example.minitest1_md4.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
@Component
@Entity
@Table(name = "car")
public class Car implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;
    private String name;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    private String idProducer;

    public Car() {
    }

    public Car(Long id, String code, String name, Double price, Type type, String idProducer) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.type = type;
        this.idProducer = idProducer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(String idProducer) {
        this.idProducer = idProducer;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Car.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Car car = (Car) target;

        ValidationUtils.rejectIfEmpty(errors, "code", "code.empty", "Code không được để trống");
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty", "Name không được để trống");
        ValidationUtils.rejectIfEmpty(errors, "price", "price.empty", "Price không được để trống");
        ValidationUtils.rejectIfEmpty(errors, "type", "type.empty", "Type không được để trống");
        ValidationUtils.rejectIfEmpty(errors, "idProducer", "idProducer.empty", "Producer không được để trống");

        if (car.getCode() != null && (!car.getCode().startsWith("CG") || car.getCode().length() != 8)) {
            errors.rejectValue("code", "code.format", "Code bắt đầu bằng CG và phải có đủ 8 kí tự");
        }

        if (car.getPrice() != null && car.getPrice() < 0) {
            errors.rejectValue("price", "price.invalid", "Price không đc để trống và là số dương");
        }
    }
}