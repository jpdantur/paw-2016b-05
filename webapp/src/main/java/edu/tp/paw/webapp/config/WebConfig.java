package edu.tp.paw.webapp.config;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import de.neuland.jade4j.spring.view.JadeViewResolver;

@EnableWebMvc
@ComponentScan({"edu.tp.paw.webapp.controller", "edu.tp.paw.webapp.form.validator", "edu.tp.paw.service", "edu.tp.paw.persistence"})
@Configuration
@EnableTransactionManagement
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
	@Autowired
  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	@Autowired
	private ApplicationContext appContext;

	@Value("classpath:schema.sql")
	private Resource schemaSql;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry
		.addResourceHandler("/favicon.ico")
		.addResourceLocations("/resources/favicon.ico");
		
		registry
		.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/");
		
	}

	/**
	 * 
	 * @return Spring Template Loader
	 */
	@Bean
	public SpringTemplateLoader templateLoader() {
		final SpringTemplateLoader templateLoader = new SpringTemplateLoader();
		templateLoader.setBasePath("/WEB-INF/jade/");
		templateLoader.setEncoding("UTF-8");
		templateLoader.setSuffix(".jade");
		return templateLoader;
	}


	/**
	 * @return Jade Configuration
	 */
	@Bean
	public JadeConfiguration jadeConfiguration() {
		final JadeConfiguration configuration = new JadeConfiguration();
		configuration.setCaching(false);
		configuration.setTemplateLoader(templateLoader());
		final Map<String, Object> sharedVariables = new HashMap<String, Object>();
		final ContextHelper context = new ContextHelper(appContext.getApplicationName());
		sharedVariables.put("context", context);
		logger.debug("app name is : {}", appContext.getApplicationName());
		configuration.setSharedVariables(sharedVariables);
		return configuration;
	}

	/**
	 * @return Jade ViewResolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		final JadeViewResolver viewResolver = new JadeViewResolver();
		viewResolver.setConfiguration(jadeConfiguration());
		return viewResolver;
	}

	/**
	 * 
	 * @return Postgres DataSource
	 */
	@Bean
	public DataSource dataSource() {

		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriverClass(org.postgresql.Driver.class);
		dataSource.setUrl("jdbc:postgresql://localhost/paw-2016b-05");
		dataSource.setUsername("paw-2016b-05");
		dataSource.setPassword("wah2Quoh");

		return dataSource;
	}
	
	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		messageSource.setCacheSeconds(5);
		
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	 
	@Override
	public Validator getValidator() {
		return validator();
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}
	
	@PostConstruct
  public void init() {
     requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
  }
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("edu.tp.paw.model");
		factoryBean.setDataSource(dataSource());
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		
		// sacar para prod
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		
		factoryBean.setJpaProperties(properties);
		return factoryBean;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
}
