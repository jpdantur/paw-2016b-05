package edu.tp.paw.webapp.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.tp.paw.webapp.auth.ItemOwnerBasedVoter;
import edu.tp.paw.webapp.auth.SiglasUserDetailsService;
@Configuration
@EnableWebSecurity
@ComponentScan({"edu.tp.paw.webapp.auth", })
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
	private static final int BCRYPT_ROUNDS = 10;

	private static final String SECRET_KEY = "X6S3prqje9ynXxEs7F+BpvaBb1i8/1Ijz2OZVXVhfrc=";
	
	@Autowired private SiglasUserDetailsService userDetailsService;
	@Autowired private ItemOwnerBasedVoter itemOwnerVoter;
	
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http
			.userDetailsService(userDetailsService)
			.sessionManagement()
//				.invalidSessionUrl("/")
			.and()
			.authorizeRequests()
				.antMatchers("/", "/store/items/**").permitAll()
				.antMatchers("/auth/login", "/auth/register").anonymous()
				.antMatchers("/store/sell/**", "/store/item/**", "/images/upload/**", "/profile/**")
					.hasRole("USER").accessDecisionManager(accessDecisionManager())
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
			.and().csrf().disable()
			.headers()
			.frameOptions().sameOrigin();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/favicon.ico", "/403", "/404", "/500", "/images/get/**");
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		final List<AccessDecisionVoter<? extends Object>> decisionVoters  = Arrays.asList(
				new WebExpressionVoter(),
				new RoleVoter(),
				new AuthenticatedVoter(),
				itemOwnerVoter
//				new ItemOwnerBasedVoter()
//	        new MinuteBasedVoter()
		);
		return new UnanimousBased(decisionVoters);
	}
	
	@Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
  }
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCRYPT_ROUNDS);  
	}
	
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManager()  throws Exception {
//		return super.authenticationManagerBean();
//	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		CustomSavedRequestAwareAuthenticationSuccessHandler successHandler = new CustomSavedRequestAwareAuthenticationSuccessHandler();
		
		successHandler.setDefaultTargetUrl("/");
		successHandler.setTargetUrlParameter("next");
		
		return successHandler;
	}
	
}
