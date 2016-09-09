package edu.tp.paw.webapp.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import de.neuland.jade4j.spring.view.JadeViewResolver;

@EnableWebMvc
@ComponentScan({"edu.tp.paw.webapp.controller", "edu.tp.paw.service", "edu.tp.paw.persistence"})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
			.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/");
	}
	
//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("WEB-INF/jsp/");
//		viewResolver.setSuffix(".jsp");
//		
//		
//		return viewResolver;
//	}
	
	@Bean
  public SpringTemplateLoader templateLoader() {
      SpringTemplateLoader templateLoader = new SpringTemplateLoader();
      templateLoader.setBasePath("/WEB-INF/jade/");
      templateLoader.setEncoding("UTF-8");
      templateLoader.setSuffix(".jade");
      return templateLoader;
  }
	

  @Bean
  public JadeConfiguration jadeConfiguration() {
      JadeConfiguration configuration = new JadeConfiguration();
      configuration.setCaching(false);
      configuration.setTemplateLoader(templateLoader());
      Map<String, Object> sharedVariables = new HashMap<String, Object>();
      sharedVariables.put("appContext", appContext);
      System.out.println("-----------------");
      System.out.println(appContext.getApplicationName());
      System.out.println("-----------------");
      
      configuration.setSharedVariables(sharedVariables);
      return configuration;
  }

  @Bean
  public ViewResolver viewResolver() {
      JadeViewResolver viewResolver = new JadeViewResolver();
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
  	dataSource.setUrl("jdbc:postgresql://localhost/paw");
  	dataSource.setUsername("martin");
//  	dataSource.setPassword(password);
  	
  	return dataSource;
  }
	
}
