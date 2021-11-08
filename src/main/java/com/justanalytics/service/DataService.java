package com.justanalytics.service;

import com.justanalytics.entity.ApiRegistration;
import com.justanalytics.query.filter.DefaultFilter;
import com.justanalytics.repository.ApiRegistrationRepository;
import com.justanalytics.repository.DataRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.justanalytics.constant.CheckAccessQuery.*;

@Service
public class DataService {

    Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private ApiRegistrationRepository apiRegistrationRepository;

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


    public List<String> findCondition(String productId, String apiId, String subscriptionId) {
        List<ApiRegistration> conditions = apiRegistrationRepository.findByProductIdAndEntityAndSubscriptionId(productId, apiId, subscriptionId);
        List<String> terminalConditions = conditions.stream().map(ApiRegistration::getCondition).collect(Collectors.toList());
        if (terminalConditions.size() == 0) {
            String defaultFilter = DefaultFilter.DEFAULT_FALSE.getDefaultFilter();
            terminalConditions.add(defaultFilter);
        }
        return terminalConditions;
    }


}
