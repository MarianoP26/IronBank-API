package com.finalproject.ironhack.security;

import com.finalproject.ironhack.consts.Consts;
import com.finalproject.ironhack.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/profile").hasAnyRole(Consts.ADMIN,Consts.HOLDER)
                .requestMatchers(HttpMethod.GET, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.GET, "/accounts/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.GET, "/send").hasRole(Consts.HOLDER)
                .requestMatchers(HttpMethod.POST, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.PATCH, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.GET, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.POST, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.PATCH, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/agents/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.GET, "/transfer/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.POST, "/transfer/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.PATCH, "/transfer/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/transfer/**").hasRole(Consts.ADMIN)
                .requestMatchers(HttpMethod.PATCH, "/tp/**").hasRole(Consts.THIRDPARTY)
                .requestMatchers(HttpMethod.PATCH, "/emit**").hasRole(Consts.ADMIN)
                .anyRequest()
                .permitAll();
        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
