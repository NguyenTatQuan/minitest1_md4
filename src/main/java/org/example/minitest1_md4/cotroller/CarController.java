package org.example.minitest1_md4.cotroller;
import org.example.minitest1_md4.model.Car;
import org.example.minitest1_md4.model.Type;
import org.example.minitest1_md4.service.ICarService;
import org.example.minitest1_md4.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private ICarService carService;

    @Autowired
    private ITypeService typeService;

    @ModelAttribute("types")
    public Iterable<Type> listTypes() {
        return typeService.findAll();
    }

    @GetMapping
    public ModelAndView listCar(Pageable pageable) {
        Page<Car> cars = carService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/car/list");
        modelAndView.addObject("cars", cars);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView listCarsSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Car> cars;
        if(search.isPresent()){
            cars = carService.findAllByCodeContaining(pageable, search.get());
        } else {
            cars = carService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/car/list");
        modelAndView.addObject("cars", cars);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/car/create");
        modelAndView.addObject("car", new Car());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("car") Car car,
                         RedirectAttributes redirectAttributes) {
        carService.save(car);
        redirectAttributes.addFlashAttribute("message", "Create new car successfully");
        return "redirect:/cars";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/car/update");
            modelAndView.addObject("car", car.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("car") Car car,
                         RedirectAttributes redirect) {
        carService.save(car);
        redirect.addFlashAttribute("message", "Update car successfully");
        return "redirect:/cars";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        carService.remove(id);
        redirect.addFlashAttribute("message", "Delete car successfully");
        return "redirect:/cars";
    }


}

