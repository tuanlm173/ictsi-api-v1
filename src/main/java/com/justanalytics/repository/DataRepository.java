package com.justanalytics.repository;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.justanalytics.config.CosmosDbProperties;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DataRepository {

    private final JdbcTemplate jdbcTemplate;

    private final CosmosClient cosmosClient;

    private final CosmosDbProperties cosmosDbProperties;

    public DataRepository(
            @Qualifier("synapseJdbcTemplate") JdbcTemplate jdbcTemplate,
            @Qualifier("cosmos") CosmosClient cosmosClient,
            CosmosDbProperties cosmosDbProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.cosmosClient = cosmosClient;
        this.cosmosDbProperties = cosmosDbProperties;
    }

    public List<Map<String, Object>> getData(String query) {
        return jdbcTemplate.queryForList(query);
    }

    public List<JSONObject> getSimpleDataFromCosmos(String containerName, String query) {
        CosmosContainer container = cosmosClient.getDatabase(cosmosDbProperties.getDatabase()).getContainer(containerName);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        CosmosPagedIterable<JSONObject> containers = container.queryItems(query, options, JSONObject.class);
        return containers.stream().collect(Collectors.toList());
    }




}
