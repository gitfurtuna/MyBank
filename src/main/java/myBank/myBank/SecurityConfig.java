package myBank.myBank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/owner/**").hasRole("owner")
//                        .requestMatchers("/admin/**").hasAnyRole("admin","owner")
//                        .requestMatchers("/client/**").hasAnyRole("client","admin","owner")
                        .requestMatchers("/owner/**").permitAll()
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/client/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/transfer/**").permitAll()
                        .requestMatchers("/hello/**").permitAll()
                        .requestMatchers("/login/**").denyAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
//                .csrf(csrf -> csrf.disable())
//                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin(formLogin -> formLogin.permitAll())
                .logout(logout -> logout.permitAll())
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    System.out.println("Access denied to: " + request.getRequestURI());
                    response.sendRedirect("/hello");
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

