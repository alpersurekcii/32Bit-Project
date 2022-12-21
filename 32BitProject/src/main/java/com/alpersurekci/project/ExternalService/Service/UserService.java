package com.alpersurekci.project.ExternalService.Service;

import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.dto.UserDto;

public interface UserService {
    //register user
    void saveUser(UserDto userDto);


}
