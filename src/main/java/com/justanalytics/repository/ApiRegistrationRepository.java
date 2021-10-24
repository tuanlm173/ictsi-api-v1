package com.justanalytics.repository;

import com.justanalytics.entity.ApiRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRegistrationRepository extends CrudRepository<ApiRegistration, Integer> {

    Boolean existsBySubscriptionId(String subscriptionId);

    Boolean existsByProductId(String productId);

    Boolean existsByProductIdAndEntityAndSubscriptionId(String productId, String apiId, String subscriptionId);

    Boolean existsByProductIdAndEntity(String productId, String apiId);

    List<ApiRegistration> findByProductIdAndEntityAndSubscriptionId(String productId, String apiId, String subscriptionId);
}
