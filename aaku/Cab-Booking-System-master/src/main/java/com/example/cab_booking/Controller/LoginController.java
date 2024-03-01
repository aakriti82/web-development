package com.example.cab_booking.Controller;

import ch.qos.logback.core.model.Model;
import com.example.cab_booking.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Login";
        }
        return "redirect:/user/homepage";
    }
    @PostMapping("/logout")
    public String logout(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        return "redirect:/user/signup";
    }
}

