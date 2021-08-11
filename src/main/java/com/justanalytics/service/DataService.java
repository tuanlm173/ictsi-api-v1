package com.justanalytics.service;

import com.justanalytics.entity.ApiRegistration;
import com.justanalytics.query.filter.DefaultFilter;
import com.justanalytics.repository.ApiRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Autowired
    private ApiRegistrationRepository apiRegistrationRepository;

    public Boolean checkAccess(String productId) {
        return apiRegistrationRepository.existsByProductId(productId);
    }

    public Boolean checkAccessv2(String productId, String apiId, String subscriptionId) {
        return apiRegistrationRepository.existsByProductIdAndEntityAndSubscriptionId(productId, apiId, subscriptionId);
    }

    public Boolean checkAccessv3(String productId, String apiId) {
        return apiRegistrationRepository.existsByProductIdAndEntity(productId, apiId);
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
