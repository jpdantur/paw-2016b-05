package edu.tp.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.tp.paw.webapp.auth.SiglasUserDetailsService;
@Configuration
@EnableWebSecurity
@ComponentScan({"edu.tp.paw.webapp.auth", })
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
	private static final int BCRYPT_ROUNDS = 10;

	private static final String SECRET_KEY = "X6S3prqje9ynXxEs7F+BpvaBb1i8/1Ijz2OZVXVhfrc=";
	
	@Autowired
	private SiglasUserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http
			.userDetailsService(userDetailsService)
			.sessionManagement()
				.invalidSessionUrl("/")
			.and().authorizeRequests()
				.antMatchers("/", "/store/items/**").permitAll()
				.antMatchers("/auth/login", "/auth/register").anonymous()
				.antMatchers("/store/sell/**", "/store/item/**", "/images/upload/**", "/profile/**").hasRole("USER")
				.antMatchers("/admin/**").hasAnyRole("ROOT", "ADMIN")
				.antMatchers("/**").authenticated()
			.and().formLogin()
				.loginPage("/auth/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(successHandler())
//				.defaultSuccessUrl("/", false)
			.and().rememberMe()
				.rememberMeParameter("rememberMe")
				.rememberMeCookieName("paw-remember-me")
				.userDetailsService(userDetailsService)
				.key(SECRET_KEY)
				.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/?l=1")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and().csrf().disable();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/favicon.ico", "/403", "/404", "/images/get/**");
	}
	
	@Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
  }
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCRYPT_ROUNDS);
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		CustomSavedRequestAwareAuthenticationSuccessHandler successHandler = new CustomSavedRequestAwareAuthenticationSuccessHandler();
		
		successHandler.setDefaultTargetUrl("/");
		successHandler.setTargetUrlParameter("next");
		
		return successHandler;
	}
	
}