package com.alpersurekci.project.Controller;

import com.alpersurekci.project.ExternalService.Service.UserService;
import com.alpersurekci.project.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
public class UserController {

    @Autowired
    UserService service;


    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    //Register page
    @GetMapping("/register")
    public String getUserRegister(Model model){
        model.addAttribute("user_register", new UserDto());
        return "register";
    }

    //Register post
    @PostMapping("/register")
    public String postUserRegister(@Valid @ModelAttribute("user_register")UserDto userDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("userRegister metodunda hata");
            return "fail";
        }
        log.info(userDto);
        service.saveUser(userDto);
        return "redirect:/";

    }
}
