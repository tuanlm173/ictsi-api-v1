package com.justanalytics.service;

import com.justanalytics.dto.CustomerDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface CustomerService {

    List<CustomerDto> findCustomer(
            Query query,
            String customerType,
            String facilityId,
            String customerName,
            String taxId,
            LocalDateTime updateTs,
            String operationType
    );

    Map<String, Object> findCustomerv2(
            Query query,
            String customerType,
            String facilityId,
            String customerName,
            String taxId,
            LocalDateTime updateTs,
            String operationType
    );
}
