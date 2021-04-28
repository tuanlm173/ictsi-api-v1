package com.justanalytics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "azure.cosmos")
public class CosmosDbProperties {

    private String uri;
    private String key;
    private String database;
    private boolean populateQueryMetrics;

}
