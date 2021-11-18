package com.justanalytics.service;

import com.justanalytics.dto.CustomerDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerDto> findCustomer(
            Query query,
            String customerType,
            String operationType
    );
}
