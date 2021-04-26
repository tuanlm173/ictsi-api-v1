package com.justanalytics.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean(name = "synapseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "synapseJdbcTemplate")
    public JdbcTemplate getSynapseJdbcTemplate(@Qualifier("synapseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
