package com.alpersurekci.project.ExternalService.Service.impl;

import com.alpersurekci.project.ExternalService.DAO.Entity.RoleEntity;
import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.ExternalService.DAO.Repository.RoleRepository;
import com.alpersurekci.project.ExternalService.DAO.Repository.UserRepository;
import com.alpersurekci.project.ExternalService.Service.UserService;
import com.alpersurekci.project.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public void saveUser(UserDto userDto) {
        UserEntity user= new UserEntity();

        user.setUserName(userDto.getUserName());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword( bCryptPasswordEncoder.encode(userDto.getPassword()));
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ADMIN");
        user.addRole(roleEntity);
        user.setUserID(5L);
        roleRepository.save(roleEntity);
        userRepository.save(user);
    }




}
