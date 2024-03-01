package com.example.cab_booking.Controller;



import com.example.cab_booking.Entity.Cars;
import com.example.cab_booking.Entity.Request;
import com.example.cab_booking.Entity.User;
import com.example.cab_booking.Pojo.CarPojo;
import com.example.cab_booking.Pojo.RequestPojo;
import com.example.cab_booking.Pojo.UserPojo;
import com.example.cab_booking.Service.CarServices;
import com.example.cab_booking.Service.RequestService;
import com.example.cab_booking.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CarServices carServices;
    private final RequestService requestService;
    @GetMapping("/homepage")
    public String getSetting(Model model, Principal principal,
                             Authentication authentication) {

        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/listOfCars";
                }
            }
        }
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Login";
        }
        List<Cars> carsList=carServices.findAll();
        model.addAttribute("carlist",carsList);
        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "index";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("signup", new UserPojo());
        return "register";
    }
    @PostMapping("/saveuser")
    public String saveUser(@Valid UserPojo userPojo,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/user/signup";
        }
        userService.save(userPojo);
        redirectAttributes.addFlashAttribute("successMsg", "User saved successfully");
        return "redirect:/login";
    }



    @GetMapping("/carsinfo/{id}")
    public String GetAamaProfile(@PathVariable("id") Integer id , Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Login";
        }
        Cars cars= carServices.findById(id);
        model.addAttribute("request", new RequestPojo());
        model.addAttribute("savecars",new CarPojo(cars));
        model.addAttribute("savecar",cars);
        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "product";
    }
    @PostMapping("/saverequest")
    public String getHire(@Valid RequestPojo requestPojo){
        requestService.save(requestPojo);
        return "redirect:/user/homepage";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id") Integer id, Model model,Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Login";
        }

        model.addAttribute("info",userService.findByEmail(principal.getName()));
        List<Request> requests= requestService.findRequestById(id);
        model.addAttribute("bookedcab",requests);

        User user = userService.findById(id);
        model.addAttribute("signup", new UserPojo(user));
        model.addAttribute("signups", user);
        return "Profile";
    }


    @PostMapping("/updateregister")
    public String GetRegister(@Valid UserPojo userPojo,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/user/signup";
        }
        userService.update(userPojo);
        redirectAttributes.addFlashAttribute("successMsg", "User updated successfully");
        return "redirect:/user/homepage";
    }


    @GetMapping("/editres/{id}")
    public String editMembers(@PathVariable("id") Integer id, Model model) {
        Request request = requestService.findById(id);
        model.addAttribute("request", new RequestPojo(request));
        return "redirect:/user/profile/{id}";
    }

    @GetMapping("/deleteres/{id}")
    public String deleteRequest(@PathVariable("id") Integer id) {
        requestService.deleteById(id);
        return "redirect:/user/homepage";
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



