package com.justanalytics.service;

import com.justanalytics.dto.TruckVisitDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TruckVisitService {

    List<TruckVisitDto> findTruckVisit(
        String truckLicenseNbr,
        String moveKind,
        LocalDateTime visitTimeFrom,
        LocalDateTime visitTimeTo,
        String size,
        List<String> terminalConditions
    );

    List<TruckVisitDto> findTruckVisitV2(
            String truckLicenseNbrs,
            String moveKinds,
            LocalDateTime visitTimeFrom,
            LocalDateTime visitTimeTo,
            String filterTruckLicenseNbrs,
            String filterMoveKinds,
            String operationType,
            String size,
            List<String> terminalConditions
    );

    List<TruckVisitDto> findTruckVisitV3(
            Query query,
            String size,
            List<String> terminalConditions
    );
}
