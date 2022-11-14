package org.springframework.samples.nt4h.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
            .antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
            .antMatchers("/users/new").permitAll()
            .antMatchers("/session/**").permitAll()
            .antMatchers("/capacities/**").permitAll()
            .antMatchers("/message/send").authenticated()
            .antMatchers("/admin/**").hasAnyAuthority("DOKTOL")
            .antMatchers("/games/**").authenticated()
            .antMatchers("/users/**").authenticated()
            // .anyRequest().denyAll()
            .and()
            .formLogin()
            /*.loginPage("/login")*/
            .failureUrl("/login-error")
            .and()
            .logout()
            .logoutSuccessUrl("/");
                // Configuración para que funcione la consola de administración
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
              "select username,password, enable "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
              "select username, authority "
                  + "from users "
                  + "where username = ?")
	      .passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
	}

}

