package org.springframework.samples.nt4h.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	final
    DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/", "/oups", "/welcome").permitAll()
            .antMatchers("/users/new", "/session/**", "/resources/**", "/webjars/**", "/h2-console/**", "/checkin", "/checkout").permitAll()
            .antMatchers("/admins/**", "/achievements/new", "/achievements/edit", "/achievements/delete", "/AuditoryUser/**","/gameInProgres/**", "/allGames").hasAnyAuthority("DOKTOL")
            .antMatchers("/achievements/**", "/games/**", "/users/**", "/cards/**",
                "/friends/**", "/turns/**", "/evasion/**", "/messages/**", "/market/**", "/reestablishment/**",
                "/api/**", "/heroAttack/**", "/enemyAttack/**", "/start/**","/AuditoryGame/get/**", "deletePlayer/**", "deleteGame/**", "/statistics/**").authenticated()
            .anyRequest().denyAll()
            .and()
            .formLogin()
            .defaultSuccessUrl("/checkin")
            /*.loginPage("/login")*/
            .failureUrl("/login-error")
            .and()
            .logout()
            .logoutSuccessUrl("/checkout");
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
        // return new BCryptPasswordEncoder();
	}

}


