package edu.tp.paw.webapp.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.tp.paw.webapp.auth.ItemOwnerBasedVoter;
import edu.tp.paw.webapp.auth.SiglasCORSFilter;
import edu.tp.paw.webapp.auth.SiglasJWTFilter;
import edu.tp.paw.webapp.auth.SiglasUserDetailsService;
@Configuration
@EnableWebSecurity
@ComponentScan({"edu.tp.paw.webapp.auth", "edu.tp.paw.webapp.security", })
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	private static final int BCRYPT_ROUNDS = 10;
	
	@Autowired private SiglasUserDetailsService userDetailsService;
	@Autowired private ItemOwnerBasedVoter itemOwnerVoter;
	
	@Autowired private SiglasJWTFilter jwtFilter;
	@Autowired private SiglasCORSFilter corsFilter;
	
	@Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
	
	@Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/favicon.ico", "/403", "/404", "/500", "/images/get/**");
//		web.ignoring().antMatchers("/api/auth/login", "/api/auth/register", "/api/auth/renew", "/api/store/most-sold", "/api/store/category-tree");
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		jwtFilter.setWhitelist("/api/auth/login", "/api/auth/register", "/api/auth/renew", "/api/store/most-sold", "/api/store/category-tree");
		
		http
			.addFilterBefore(corsFilter, ChannelProcessingFilter.class)
			.csrf()
				.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS ,"/**").permitAll()
				.antMatchers(HttpMethod.HEAD,"/**").permitAll()
				.antMatchers("/api/auth**").permitAll()
				.antMatchers("/api**").authenticated()
			.and()
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
//	@Override
//  public void configure(AuthenticationManagerBuilder auth) throws Exception {
//    // Create a default account
//    auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
//  }
	
	
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		final List<AccessDecisionVoter<? extends Object>> decisionVoters  = Arrays.asList(
				new WebExpressionVoter(),
				new RoleVoter(),
				new AuthenticatedVoter(),
				itemOwnerVoter
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
	
//	@Bean
//	public AuthenticationSuccessHandler successHandler() {
//		CustomSavedRequestAwareAuthenticationSuccessHandler successHandler = new CustomSavedRequestAwareAuthenticationSuccessHandler();
//		
//		successHandler.setDefaultTargetUrl("/");
//		successHandler.setTargetUrlParameter("next");
//		
//		return successHandler;
//	}
	
}
