package com.justanalytics.service;

import com.justanalytics.repository.ApiRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Autowired
    private ApiRegistrationRepository apiRegistrationRepository;

    public Boolean checkAccess(String subscriptionId) {
        return apiRegistrationRepository.existsBySubscriptionId(subscriptionId);
    }
}
