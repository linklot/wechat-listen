package com.lli.mp.config;

import com.lli.mp.service.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService mongoUserDetailsService() {
		return new CustomerUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = mongoUserDetailsService();
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
					.antMatchers("/mp/queryWechatAuthCode", "/mp/queryWechatOpenID",
							"/mp/index", "/mp/mediaDetail",
							"/mp/local_index", "/mp/local_mediaDetail",
							"/js/**", "/audio/**", "/css/**", "/image/**", "/fonts/**",
							"/mp/coverImg/**", "/mp/audio/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.permitAll()
					.loginPage("/login")
					.and()
				.logout()
					.permitAll();
	}
}
