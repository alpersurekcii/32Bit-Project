package com.alpersurekci.project.ExternalService.DAO.Repository;

import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntitiesByUserNameEquals(String username);


}
