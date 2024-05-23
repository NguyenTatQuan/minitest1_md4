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
    private String code;
    private String name;
    private String producer;
    private String price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;


    public Car() {

    }

    public Car(Long id, String code, String name, String producer, String price, Type type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.type = type;
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Car.class.isAssignableFrom(clazz);
    }

    @Override
        public void validate(Object target, Errors errors) {
            Car car = (Car) target;
            String code = car.getCode();
            String name = car.getName();
            String producer = car.getProducer();
            String price = car.getPrice();
            Type type1 = car.getType();
            ValidationUtils.rejectIfEmpty(errors,"code", "code.emty", "Không được bỏ trống");
            if(!code.matches("(^CG[a-zA-Z0-9]{6}$)")){
                errors.rejectValue("code","code.matches", "Phải có 8 kí tự và bắt đầu bằng CG.");
            }
            ValidationUtils.rejectIfEmpty(errors,"name", "name.emty", "Không được bỏ trống");
            ValidationUtils.rejectIfEmpty(errors,"producer", "producer.emty", "Không được bỏ trống");
            ValidationUtils.rejectIfEmpty(errors,"price", "price.emty", "Không được bỏ trống");
            ValidationUtils.rejectIfEmpty(errors,"type", "type.emty", "Không được bỏ trống");
        }
}