package co.edu.usco.SecuritySpring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestAuthController {

    @GetMapping
    public String showIndex() {
        return "Hello";
    }

    @GetMapping("/new")
    public String showForm() {
        return "Create";
    }
    
    @GetMapping("/edit")
    public String update() {
        return "Edit";
    }
    
    @GetMapping("/delete")
    public String delete() {
        return "Delite";
    }
}
