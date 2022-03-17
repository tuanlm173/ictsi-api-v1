package com.justanalytics.repository;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.azure.cosmos.util.CosmosPagedFlux;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.justanalytics.config.CosmosDbProperties;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataRepository {

//    private final JdbcTemplate jdbcTemplate;

    private final CosmosClient cosmosClient;

    private final CosmosDbProperties cosmosDbProperties;

    private final CosmosAsyncClient cosmosAsyncClient;

    public DataRepository(
//            @Qualifier("synapseJdbcTemplate") JdbcTemplate jdbcTemplate,
            @Qualifier("cosmos") CosmosClient cosmosClient,
            CosmosDbProperties cosmosDbProperties, CosmosAsyncClient cosmosAsyncClient) {
//        this.jdbcTemplate = jdbcTemplate;
        this.cosmosClient = cosmosClient;
        this.cosmosDbProperties = cosmosDbProperties;
        this.cosmosAsyncClient = cosmosAsyncClient;
    }

//    public List<Map<String, Object>> getData(String query) {
//        return jdbcTemplate.queryForList(query);
//    }

    public List<JSONObject> getSimpleDataFromCosmos(String containerName, String query) {
        CosmosContainer container = cosmosClient.getDatabase(cosmosDbProperties.getDatabase()).getContainer(containerName);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        CosmosPagedIterable<JSONObject> containers = container.queryItems(query, options, JSONObject.class);
        return containers.stream().collect(Collectors.toList());
    }

//    public Flux<FeedResponse<JSONObject>> getSimpleDataFromCosmosAsync(String containerName, String query) {
//        CosmosAsyncContainer container = cosmosAsyncClient.getDatabase(cosmosDbProperties.getDatabase()).getContainer(containerName);
//        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
//        Mono<List<JSONObject>> results = container.queryItems(query, options, JSONObject.class).collect(Collectors.toList());
//        CosmosPagedFlux<JSONObject> results2 = container.queryItems(query, options, JSONObject.class);
//        return results2.byPage();
//
//    }




}
