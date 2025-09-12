package com.akshat.practice.app.config;

import com.akshat.practice.app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserService userService;

	public SecurityConfig(UserService userService) {
	    this.userService = userService;
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    	
    	/*Using lambda
		security.csrf(customizer -> customizer.disable()); // disabling csrf will make it use just basic auth with id pass
		security.authorizeHttpRequests(request -> request.anyRequest().authenticated()); //Won't allow access without authentication
		security.formLogin(Customizer.withDefaults());//browser login form
		security.httpBasic(Customizer.withDefaults()); //accept postman basic auth
		security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //Means creates a session on each hit, will interrupt browser as it will ask for login page again and again because everytime session is changing
    	return security.build();*/
    	
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
    	
    	/*using builder pattern*/
    	return security
    			.csrf(AbstractHttpConfigurer::disable) // disabling csrf will make it use just basic auth with id pass
				.authorizeHttpRequests(request -> request.anyRequest().authenticated()) //Won't allow access without authentication
				//.formLogin(Customizer.withDefaults())//browser login form
				.httpBasic(Customizer.withDefaults()) //accept postman basic auth
				.sessionManagement(session -> 
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Means creates a session on each hit, will interrupt browser as it will ask for login page again and again because everytime session is changing
		    	.build();
	}
    
//    @Bean
//    UserDetailsService userDetailsService() {
//    	UserDetails user1 = User
//    			.withDefaultPasswordEncoder()
//    			.username("Akshat")
//    			.password("Akshat@0o0")
//    			.roles("EMPLOYEE")
//    			.build();
//    	
//    	return new InMemoryUserDetailsManager(user1);
//    }
    
    @Bean
    AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    	provider.setUserDetailsService(userService);
    	return provider;
    }
}
