package com.alpersurekci.project.Controller;

import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.ExternalService.Service.UserService;
import com.alpersurekci.project.Security.JwtUtils;
import com.alpersurekci.project.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * user controller
 * User login ve registerın bulunduğu sınıftır.
 * @author Alper Sürekçi
 */
@Log4j2
@Controller
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String authenticateUser(@Valid @ModelAttribute("user") UserDto userDto) {

      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      userDto.getUserName(),
                      userDto.getPassword()
              ));
      SecurityContextHolder.getContext().setAuthentication(authentication);
              String token = jwtUtils.generateJwtToken(authentication);
        System.out.println(userDto);
        System.out.println(token);



        return "redirect:/";
    }


    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("user", new UserDto());
        return "login";
    }

    /**
     * User için get register metodu
     * @param model model objemiz
     * @return
     */
    //Register page
    @GetMapping("/register")
    public String getUserRegister(Model model){
        model.addAttribute("user_register", new UserDto());
        return "register";
    }

    /**
     * User register için post metodu
     * @param userDto kullanıcı bilgileri
     * @param bindingResult
     * @return
     */
    //Register post
    @PostMapping("/register")
    public String postUserRegister(@Valid @ModelAttribute("user_register")UserDto userDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("userRegister method failed");
            return "fail";
        }
        log.info(userDto);
        service.saveUser(userDto);
        return "redirect:/";

    }
}
