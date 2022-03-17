package com.justanalytics.service;

import com.azure.cosmos.models.FeedResponse;
import com.justanalytics.dto.ApiConfigDto;
import com.justanalytics.repository.DataRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.justanalytics.constant.CheckAccessQuery.*;
import static com.justanalytics.constant.CheckTerminalConditionQuery.*;

@Service
public class DataService {

    Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private DataRepository dataRepository;

    public Boolean checkAccessFromCosmos(String productId, String apiId, String subscriptionId) {
        String query = CHECK_ACCESS_QUERY;
        query = String.format(query, subscriptionId, productId, apiId);
        List<JSONObject> accessData = new ArrayList<>();
        List<JSONObject> accessDataFromCosmos = dataRepository.getSimpleDataFromCosmos(CHECK_ACCESS_CONTAINER_NAME, query);
        logger.info("Checking access: {}", query);
        if (accessDataFromCosmos != null) accessData = accessDataFromCosmos;
        return accessData.size() != 0;
    }


//    public List<String> findCondition(String productId, String apiId, String subscriptionId) {
//        List<ApiRegistration> conditions = apiRegistrationRepository.findByProductIdAndEntityAndSubscriptionId(productId, apiId, subscriptionId);
//        List<String> terminalConditions = conditions.stream().map(ApiRegistration::getCondition).collect(Collectors.toList());
//        if (terminalConditions.size() == 0) {
//            String defaultFilter = DefaultFilter.DEFAULT_FALSE.getDefaultFilter();
//            terminalConditions.add(defaultFilter);
//        }
//        return terminalConditions;
//    }

    public List<String> findConditionCosmos(String productId, String apiId, String subscriptionId) {
        String query = CHECK_TERMINAL_CONDITION_QUERY;
        query = String.format(query, subscriptionId, productId, apiId);
        List<JSONObject> rawTerminalConditions = dataRepository.getSimpleDataFromCosmos(CHECK_TERMINAL_CONDITION_CONTAINER_NAME, query);
        logger.info("Checking terminal condition: {}", query);
        List<String> results = new ArrayList<>();
        if (rawTerminalConditions != null) {
            List<ApiConfigDto> terminalConditions = new ArrayList<>(rawTerminalConditions.size());
            for (JSONObject data : rawTerminalConditions) {
                String condition = String.valueOf(data.get("condition"));
                terminalConditions.add(ApiConfigDto.builder().condition(condition).build());
            }
            results = terminalConditions.stream().map(ApiConfigDto::getCondition).collect(Collectors.toList());
        }
        return results;

    }

//    //TODO: test async
//    public Flux<FeedResponse<JSONObject>> findAsyncData(String containerName, String query) {
//        return dataRepository.getSimpleDataFromCosmosAsync(containerName, query);
//    }


}
