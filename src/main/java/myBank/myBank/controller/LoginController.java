package myBank.myBank.controller;

import jakarta.servlet.http.HttpSession;
import myBank.myBank.model.User;
import myBank.myBank.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "hello";
    }


    @PostMapping("/hello")
    public String login(@ModelAttribute User user, Model model, HttpSession session) {
        try {
            userService.loginUser(user);
            User currentUser = userService.getUser(user);
            session.setAttribute("user", currentUser);
            switch (userService.getUserRole(currentUser)) {
                case "client":
                    return "redirect:/client";
                case "admin":
                    return "redirect:/admin";
                case "owner":
                    return "redirect:/owner";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "hello";
        }
        return "redirect:/";
    }
}
