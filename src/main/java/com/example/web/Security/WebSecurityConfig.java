package com.example.web.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import com.example.web.Services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
		
	@Bean
		protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			System.out.println("filter");
			http.authorizeHttpRequests(requests -> requests
					.antMatchers("/webjars/**").permitAll()
					.antMatchers("/").permitAll()
					.antMatchers("/register").permitAll()
					.antMatchers("/process_register").permitAll()
					.antMatchers("/verify").permitAll()
					.antMatchers("/API/**").permitAll()
					.antMatchers("/forgot_password").permitAll()
					.antMatchers("/reset_password").permitAll()
					.antMatchers("/users/**").hasAnyAuthority("ADMIN","CREATER", "EDITOR","USER")
					.antMatchers("/products/")
					.hasAnyAuthority("USER", "CREATER", "EDITOR", "ADMIN")
					.antMatchers("/products/create")
					.hasAnyAuthority("ADMIN", "CREATER")
					.antMatchers("/products/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
					.antMatchers("/products/delete/**").hasAuthority("ADMIN")
					.antMatchers("/cart/add/**").hasAnyAuthority("USER", "CREATER", "EDITOR", "ADMIN").anyRequest().authenticated())
					.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/").permitAll())
					.logout(logout -> logout.permitAll())
					.exceptionHandling(handling -> handling.accessDeniedPage("/403"))
					.csrf().disable();
			return http.build();
		}
}
