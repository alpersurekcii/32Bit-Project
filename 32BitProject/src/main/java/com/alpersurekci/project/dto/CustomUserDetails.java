package com.alpersurekci.project.dto;
/**
 * Spring Security de kullanılan giriş yapan kullanıcı bilgilerini içermektedir.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.DAO.Entity.RoleEntity;
import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

  private Long id;

  private String username;

  @JsonIgnore
  private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }



    public static CustomUserDetails build(UserEntity user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getUserID(),
                user.getUserName(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetails user = (CustomUserDetails) o;
        return Objects.equals(id, user.id);
    }
}