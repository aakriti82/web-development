package com.example.cab_booking.Controller;


import com.example.cab_booking.Entity.Cars;
import com.example.cab_booking.Entity.Request;
import com.example.cab_booking.Pojo.CarPojo;
import com.example.cab_booking.Pojo.UserPojo;
import com.example.cab_booking.Service.CarServices;
import com.example.cab_booking.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private  final CarServices carServices;
    private  final RequestService requestService;

    @GetMapping("/addcars")
    public String getSignupPage(Model model) {
        model.addAttribute("addcars", new CarPojo());
        return "Admin/Addcars";
    }
    @PostMapping("/savecars")
    public String saveUser(@Valid CarPojo carPojo,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/admin/addcars";
        }
        carServices.save(carPojo);
        redirectAttributes.addFlashAttribute("successMsg", "User saved successfully");
        return "redirect:/admin/listOfCars";
    }


    @GetMapping("/listOfCars")
    public String getAllCars(Model model){
        List<Cars> hireList=carServices.findAll();
        model.addAttribute("carslist",hireList);
        return "Admin/ViewCars";
    }


    @GetMapping("/editcars/{id}")
    public String editMembers(@PathVariable("id") Integer id, Model model) {
        Cars cars = carServices.findById(id);
        model.addAttribute("addcars", new CarPojo(cars));
        return "Admin/Addcars";
    }

    @GetMapping("/deletecars/{id}")
    public String deleteApplication(@PathVariable("id") Integer id) {
        carServices.deleteById(id);
        return "redirect:/admin/listOfCars";
    }


    @GetMapping("/listOfrequest")
    public String getAllRequest(Model model){
        List<Request> requests=requestService.findAll();
        model.addAttribute("requestlist",requests);
        return "/Admin/Request";
    }

    public Map<String, String> validateRequest(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;

    }

}
