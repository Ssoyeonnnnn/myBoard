package com.example.myBoard.controller;

import com.example.myBoard.dto.UserCreateForm;
import com.example.myBoard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserAccountController {
    private final UserService userService;

    public UserAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }


    @GetMapping("signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup";
    }

    @PostMapping("signup")
    public String signup(@Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        try {
            userService.createUser(userCreateForm);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
            return "signup";
        } catch (Exception e){
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }
        return "redirect:/";
    }
}
