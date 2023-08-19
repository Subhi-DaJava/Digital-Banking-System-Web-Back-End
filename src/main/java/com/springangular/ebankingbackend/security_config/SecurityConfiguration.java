package com.springangular.ebankingbackend.security_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String pwd = passwordEncoder().encode("123456");

        return new InMemoryUserDetailsManager(

                User.withUsername("UserA").password(pwd).authorities("USER").build(),
                User.withUsername("UserB").password(pwd).authorities("USER").build(),
                User.withUsername("Admin").password(pwd).authorities("USER", "ADMIN").build()

        );
    }
   /*
    Type chrome://restart in the address bar and chrome, with all its apps that are running in background, will restart and the Auth password cache will be cleaned.
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // authentication stateless
                .csrf(AbstractHttpConfigurer::disable) //  .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()) // type of authentication
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
