package com.justanalytics.service;

import com.justanalytics.repository.ApiRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
