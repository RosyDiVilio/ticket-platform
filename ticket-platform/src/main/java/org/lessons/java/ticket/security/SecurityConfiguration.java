package org.lessons.java.ticket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//con questa annotazione tutti i bean verranno chiamati e istanziati dal IoC container 
//(container che prende il potere di gestire ciò che gli serve)
@Configuration
public class SecurityConfiguration {
	
	//preparo configurazione applicativo per far si che accetti queto comportamento
	@SuppressWarnings("removal")
	//metodo che recupera meccanismi di autorizzazione per le risorse che ho a disposizione
	//httpsecurity mi consente di fare ciò
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests()
		//vado a qualificare dove vado ad applicare configurazione
		.requestMatchers(HttpMethod.POST, "/tickets/**").hasAuthority("ADMIN")
		.requestMatchers("/tickets/index").hasAuthority("ADMIN")
		.requestMatchers(HttpMethod.POST, "/operators/**").hasAuthority("USER")
		.requestMatchers("/operators/**").hasAuthority("USER")
		.requestMatchers("/**").permitAll()
		.requestMatchers("/user").hasAuthority("USER")
		.requestMatchers("/admin").hasAuthority("ADMIN")
		.and().formLogin() //Abilita il login tramite un modulo HTML
		.and().logout()
		.and().exceptionHandling();  //gestisce eccezioni
		
		return http.build();
	}
	
	//genera servizio che gestisce recupero informazioni user
	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}
	
	//come gestire codifica password
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	//gestire autenticazione gestendo le relazione tra di loro
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

}
