package edu.tp.paw.persistence;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
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
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
         return new JpaTransactionManager(emf);
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
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }
} 
