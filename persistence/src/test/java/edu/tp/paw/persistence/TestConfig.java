package edu.tp.paw.persistence;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;

@ComponentScan({ "edu.tp.paw.persistence", })
@Configuration
public class TestConfig {
	
	@Value("classpath:schema.sql")
	private Resource schemaSql;
	
	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setUrl("jdbc:hsqldb:mem:paw");
		ds.setDriverClass(JDBCDriver.class);	
		ds.setUsername("ha");
		ds.setPassword("");
		
		return ds;
	}
	
	  @Bean
	  public DatabasePopulator databasePopulator() {
	  	final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
	  	
	  	databasePopulator.addScript(schemaSql);
	  	
	  	return databasePopulator;
	  }
	  
	  @Bean
	  public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
	  	
	  	final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
	  	
	  	dataSourceInitializer.setDataSource(dataSource);
	  	dataSourceInitializer.setDatabasePopulator(databasePopulator());
	  	
	  	return dataSourceInitializer;
	  }
} 
