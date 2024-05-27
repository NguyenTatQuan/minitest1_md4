package org.example.minitest1_md4.cotroller;


import org.example.minitest1_md4.exception.DuplicateCodeException;
import org.example.minitest1_md4.model.Car;
import org.example.minitest1_md4.model.Type;
import org.example.minitest1_md4.service.ICarService;
import org.example.minitest1_md4.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CarController {
    @Autowired
    private ICarService carService;

    @Autowired
    private ITypeService typeService;

    @ModelAttribute("types")
    public Iterable<Type> listType() {
        return typeService.findAll();
    }

    @GetMapping("/car")
    public ModelAndView listCar() {
        ModelAndView modelAndView = new ModelAndView("list");
        Iterable<Car> cars = carService.findAll();
        modelAndView.addObject("cars", cars);
        return modelAndView;
    }

    @GetMapping("/cars/search")
    public ModelAndView search(@RequestParam("search") String search, @RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("list");
        PageRequest pageable = PageRequest.of(page, 10);
        modelAndView.addObject("cars", carService.findAllByCodeContaining(pageable, search));
        modelAndView.addObject("search", search);
        return modelAndView;
    }
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("car", new Car());
        return "create";
    }
    @PostMapping("/save")
    public String save(Car car) throws DuplicateCodeException {
        carService.save(car);
        return "redirect:/car";
    }

    @PostMapping("/create")
    public String checkValidation(@Valid Car car, Model model, BindingResult bindingResult) throws DuplicateCodeException {
        new Car().validate(car, bindingResult);
        if (bindingResult.hasErrors()) {
            return "create";
        }
        carService.save(car);
        model.addAttribute("message", "New car created successfully");
        return "redirect:/car";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/update");
            modelAndView.addObject("car", car.get());
            return modelAndView;
        } else {
            return new ModelAndView("inputs-not-acceptable");
        }
    }

    @PostMapping("/update/{id}")
    public String checkValidationUpdate(@Valid Car car, Model model, BindingResult bindingResult)throws DuplicateCodeException {
        new Car().validate(car, bindingResult);
        if (bindingResult.hasErrors()) {
            return "update";
        }
        carService.save(car);
        model.addAttribute("message", "car updated successfully");
        return "redirect:/car";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        carService.remove(id);
        redirect.addFlashAttribute("message", "Car deleted successfully");
        return "redirect:/car";
    }
    @ExceptionHandler(DuplicateCodeException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/inputs-not-acceptable");
    }
}