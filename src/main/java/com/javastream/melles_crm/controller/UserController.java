package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.User;
import com.javastream.melles_crm.model.UserType;
import com.javastream.melles_crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allUsers(Model model) {
        List<User> users = userService.findAll();

        User user = new User();
        user.setUserType(UserType.CLIENT);

        model.addAttribute("users", users);
        model.addAttribute("newUser", user);

        return "clients/users";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        userService.save(user);

        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") String id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "clients/userEdit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.save(user);

        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteById(id);

        return "redirect:/clients";
    }
}