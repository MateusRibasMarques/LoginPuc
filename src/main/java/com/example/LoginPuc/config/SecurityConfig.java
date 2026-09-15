package com.example.LoginPuc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserConfig userConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. A SOLUÇÃO DO POSTMAN: Isso desativa a trava para testes externos
                .csrf(csrf -> csrf.disable()) 
                
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/login/**").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/css/**").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/register").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/register").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/recover-password").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/recover-password").permitAll() 
                        
                        // 2. CORREÇÃO DO ERRO: Removido o HttpMethod.GET para liberar geral
                        .requestMatchers("/error").permitAll() 
                        
                        .requestMatchers("/admin/**").hasRole("ADMIN") 
                        .anyRequest().authenticated() 
                )
                .formLogin(form -> form
                        .loginPage("/login") 
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/admin"); 
                            } else {
                                response.sendRedirect("/home"); 
                            }
                        })
                        .failureHandler((request, response, authentication) -> {
                            response.sendRedirect("/error"); 
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") 
                        .logoutSuccessUrl("/login?logout=true") 
                        .permitAll());
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username(userConfig.getUserUsername())
                .password(passwordEncoder().encode(userConfig.getUserPassword())) // Codificar a senha
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username(userConfig.getAdminUsername())
                .password(passwordEncoder().encode(userConfig.getAdminPassword()))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}