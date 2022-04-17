package com.bso.companycob.infrastructure.data.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class DataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties("application.datasource.configuration")
	public DataSource dataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	@ConfigurationProperties("application.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

}
