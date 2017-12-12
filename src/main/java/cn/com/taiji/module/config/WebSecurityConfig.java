package cn.com.taiji.module.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
		.and().withUser("root").password("root").roles("USER")
		.and().withUser("you").password("you").roles("YOU")
		.and().withUser("ad").password("ad").roles("ADMIN");
	}
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/webjars/**", "/signup", "/about","/touserlist","/bootstrap3/**","/getuserlist").permitAll()
		.antMatchers("/admin/**").hasRole("USER")
		.anyRequest()
		.authenticated()
		.and().formLogin().loginPage("/login").permitAll().and().logout();
		http.csrf().disable();
		
	
	}
}
