package com.justanalytics.repository;

import com.justanalytics.entity.ApiRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRegistrationRepository extends CrudRepository<ApiRegistration, Integer> {

    Boolean existsBySubscriptionId(String subscriptionId);
}
