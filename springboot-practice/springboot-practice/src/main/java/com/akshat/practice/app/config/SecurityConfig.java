package com.akshat.practice.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    	
    	/*Using lambda*/
		security.csrf(customizer -> customizer.disable()); // disabling csrf will make it use just basic auth with id pass
		security.authorizeHttpRequests(request -> request.anyRequest().authenticated()); //Won't allow access without authentication
		security.formLogin(Customizer.withDefaults());//browser login form
		security.httpBasic(Customizer.withDefaults()); //accept postman basic auth
		security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //Means creates a session on each hit, will interrupt browser as it will ask for login page again and again because everytime session is changing
    	return security.build();
    	
    	/*Normal Imperative way
    	Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
			@Override
			public void customize(CsrfConfigurer<HttpSecurity> customizer) {
				customizer.disable();
			}
		};
		security.csrf(custCsrf);
		
		Customizer<AuthorizationManagerRequestMatcherRegistry> t = new Customizer<AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry>() {
			@Override
			public void customize(AuthorizationManagerRequestMatcherRegistry to) {
				to.anyRequest();
			}
		};
		security.authorizeHttpRequests()*/
    	//return security.build();
	}
}
