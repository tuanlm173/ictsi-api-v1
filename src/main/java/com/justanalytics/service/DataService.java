package com.justanalytics.service;

import com.justanalytics.entity.ApiRegistration;
import com.justanalytics.repository.ApiRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String findCondition(String productId, String apiId, String subscriptionId) {
        List<ApiRegistration> conditions = apiRegistrationRepository.findByProductIdAndEntityAndSubscriptionId(productId, apiId, subscriptionId);
        if (conditions.size() == 0) return "";
        else return conditions.stream().findFirst().get().getCondition();
    }
}
