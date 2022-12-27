package com.alpersurekci.project.Security;

import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.ExternalService.DAO.Repository.UserRepository;
import com.alpersurekci.project.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * kullanıcı veri tabanında aranır
     * @param username kullanıcı username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntitiesByUserNameEquals(username);
                 if(user!=null){
                     return CustomUserDetails.build(user);
                 }else{
                     log.info("Kullanıcı bulunamadı");
                     return null;
                 }


    }

}