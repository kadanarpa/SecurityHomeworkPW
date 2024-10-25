
package co.edu.usco.Security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    
    @GetMapping
    public String showIndex() {
        return "index";
    }
    
    @GetMapping("/new")
    public String showAdd() {
        return "new";
    }
    
    @GetMapping("/edit")
    public String showEdit() {
        return "edit";
    }
    
    @GetMapping("/delete")
    public String showDelete() {
        return "delete";
    }
    
    @GetMapping("/loginPage")
    public String showLogin() {
        return "login";
    }
    
    @GetMapping("/403")
    public String showForbidden() {
        return "403";
    }
}
