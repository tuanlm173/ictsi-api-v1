package com.justanalytics.service;

import com.justanalytics.dto.TruckTransactionsDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TruckingTransactionsService {

    List<TruckTransactionsDto> findTruckTransactions(
            Query query,
            String truckCompany,
            String truckPlate,
            String uniqueKey,
            LocalDateTime truckVisitTimeFrom,
            LocalDateTime truckVisitTimeTo,
            String operationType,
            List<String> terminalConditions
    );
}
