package com.dropwinsystem.app.configurations;

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
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/ws/**", "/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
				.requestMatchers("/webjars/**").permitAll()
			    .anyRequest().permitAll())
				.csrf(csrf -> csrf.disable())
				.logout((logout) -> logout 
						.logoutSuccessUrl("/loginForm")
						.invalidateHttpSession(true));
		
	    return http.build();
	}
}