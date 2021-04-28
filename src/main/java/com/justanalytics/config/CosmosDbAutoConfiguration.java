package com.justanalytics.config;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.*;
import com.azure.cosmos.implementation.RequestOptions;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;

import java.text.MessageFormat;

@Configuration
@EnableConfigurationProperties(CosmosDbProperties.class)
@PropertySource("classpath:application.properties")
@Slf4j
public class CosmosDbAutoConfiguration extends AbstractCosmosConfiguration {

    protected final RequestOptions requestOptions = new RequestOptions();
    private CosmosDbProperties dbProperties;
    private AzureKeyCredential azureKeyCredential;

    @Autowired
    public CosmosDbAutoConfiguration(CosmosDbProperties cosmosDbProperties) {
        this.dbProperties = cosmosDbProperties;
        this.requestOptions.setConsistencyLevel(ConsistencyLevel.SESSION);
        this.requestOptions.setScriptLoggingEnabled(true);
    }

    @Bean
    @Qualifier("cosmos")
    public CosmosClient getCosmosDBConfigBuilder() {
        try {
            String cosmosUri = dbProperties.getUri();
            String cosmosKey = dbProperties.getKey();

            azureKeyCredential = new AzureKeyCredential(cosmosKey);
            DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
            GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
            return new CosmosClientBuilder()
                    .endpoint(cosmosUri)
                    .credential(azureKeyCredential)
                    .directMode(directConnectionConfig, gatewayConnectionConfig)
                    .buildClient();
        } catch (Exception ex) {
            log.error(MessageFormat.format("getConfig failed with error: {0}",
                    ex.getMessage()));

            throw ex;
        }
    }

    @Override
    @Bean
    public CosmosConfig cosmosConfig() {
        return CosmosConfig.builder()
                .enableQueryMetrics(dbProperties.isPopulateQueryMetrics())
                .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
                .build();
    }

    @Override
    protected String getDatabaseName() {
        return dbProperties.getDatabase();
    }

    private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

        @Override
        public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
            log.info("Response Diagnostics {}", responseDiagnostics);
        }
    }
}