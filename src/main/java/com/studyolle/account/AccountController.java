package com.studyolle.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AccountController {

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }
}
