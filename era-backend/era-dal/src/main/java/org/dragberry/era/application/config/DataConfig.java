package org.dragberry.era.application.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.dragberry.era.application.DummyDataBean;
import org.dragberry.era.dao.DataAccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.googlecode.flyway.core.Flyway;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {DataAccessObject.class, DummyDataBean.class})
@PropertySources({ @PropertySource("classpath:database.properties") })
public class DataConfig {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.userName";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";

	private static final String PROPERTY_NAME_DATABASE_DIALECT = "db.dialect";
	private static final String PROPERTY_NAME_DATABASE_SHOW_SQL = "db.showSql";
	private static final String PROPERTY_NAME_DATABASE_FORMAT_SQL = "db.formatSql";

	@Autowired
	private Environment env;
	
	@Bean @DependsOn("flyway")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("org.dragberry.era.domain");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	public Properties hibernateProperties() {
		Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty(PROPERTY_NAME_DATABASE_DIALECT));
        properties.put("hibernate.show_sql", env.getRequiredProperty(PROPERTY_NAME_DATABASE_SHOW_SQL));
        properties.put("hibernate.format_sql", env.getRequiredProperty(PROPERTY_NAME_DATABASE_FORMAT_SQL));
        return properties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}
	
	@Bean
	public JpaTransactionManager TransactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource());
		flyway.setLocations("classpath:db/migration/", "classpath:org.dragberry.era.db.migration");
		flyway.setInitOnMigrate(true);
		return flyway;
	}
}
