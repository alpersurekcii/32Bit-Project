package com.alpersurekci.project.Security;

import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.ExternalService.DAO.Repository.UserRepository;
import com.alpersurekci.project.ExternalService.Service.UserService;
import com.alpersurekci.project.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =repository.findUserEntitiesByUserNameEquals(username);
        if(user!=null){
             return new CustomUserDetails(user);
        }else{
            throw new UsernameNotFoundException("Invalid username and password");
        }
    }
}
