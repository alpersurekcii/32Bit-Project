package com.alpersurekci.project.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSpringSecurity {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http .authorizeHttpRequests(authorize -> {
                    try {
                        authorize.requestMatchers("/register").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .requestMatchers("/ws").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/soap/**").permitAll()
                                .requestMatchers("/").hasAnyAuthority("ADMIN")
                                .requestMatchers("/save/customer").hasAnyAuthority("ADMIN")
                                .requestMatchers("/delete/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/customer/**").hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .formLogin().loginPage("/login");
        http
                .logout(logout -> logout
                        .logoutUrl("logout")
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(logout.getLogoutSuccessHandler())
                );


        http.authenticationProvider(authenticationProvider());
        return http.build();
    }


}
