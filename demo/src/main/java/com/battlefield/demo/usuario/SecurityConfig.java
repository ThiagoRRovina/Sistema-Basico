package com.battlefield.demo.usuario;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público à sua página de login e ao endpoint de salvar usuário
                        .requestMatchers("/telaLogin", "/telaLogin/salvar").permitAll()
                        // Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Define a sua página de login personalizada
                        .loginPage("/telaLogin")
                        // Define o endpoint para onde o formulário de login envia os dados
                        .loginProcessingUrl("/telaLogin/login")
                        // Para onde ir após um login bem-sucedido
                        .defaultSuccessUrl("/Home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        // Para onde ir após o logout
                        .logoutSuccessUrl("/telaLogin")
                        .permitAll()
                );

        return http.build();
    }
}
