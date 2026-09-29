package br.com.mesquita.security;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.mesquita.service.AcessoUsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**").permitAll()
                .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                
            )
            .formLogin(form -> form
    			.loginPage("/login")
    			.loginProcessingUrl("/perform_login")
    			.defaultSuccessUrl("/", false)
                .failureUrl("/login?error=true")
    			.permitAll()
    		)
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Hashing algorithm for passwords
    }

    @Bean
    AuthenticationManager authenticationManager(AcessoUsuarioService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
    
    /**
     * Este método serve para gerar senhas criptografadas
     */
    void geraSenha() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        IO.println(encoder.encode("senha"));
    }
}
