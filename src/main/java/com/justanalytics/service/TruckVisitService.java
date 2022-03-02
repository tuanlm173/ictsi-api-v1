package com.justanalytics.service;

import com.justanalytics.dto.TruckVisitDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TruckVisitService {


    List<TruckVisitDto> findTruckVisit(
            Query query,
            String facilityId,
            String truckLicenseNbrs,
            String visitPhases,
            String carrierOperatorNames,
            LocalDateTime visitTimeFrom,
            LocalDateTime visitTimeTo,
            String lastVisitFlag,
            String operationType,
            List<String> terminalConditions
    );

    List<TruckVisitDto> findTruckVisitByTruckPlate(
            Query query,
            String visitPhases,
            String lastVisitFlag,
            String operationType
    );
}
