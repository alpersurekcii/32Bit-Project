package com.alpersurekci.project.Security;

import com.alpersurekci.project.Security.jwt.AuthEntryPointJwt;
import com.alpersurekci.project.Security.jwt.AuthTokenFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSpringSecurity {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public Filter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
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
        /*
      http .authorizeHttpRequests(authorize -> {
                    try {
                        authorize.requestMatchers("/register").denyAll()
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
                );*/
        http.cors().and().csrf().disable()
                        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and().authorizeHttpRequests().requestMatchers("/login").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/register").permitAll()
                .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated();
        http.formLogin().loginPage("/login");
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
