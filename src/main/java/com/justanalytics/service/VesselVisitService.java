package com.justanalytics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.justanalytics.dto.VesselVisitDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface VesselVisitService {

    List<VesselVisitDto> findVesselVisit (
            Query query,
            String facilityId,
            String carrierName,
            String carrierOperatorId,
            String carrierOperatorName,
            String carrierVisitId,
            String serviceId,
            String visitPhase,
            String ibVyg,
            String obVyg,
            LocalDateTime etaFrom,
            LocalDateTime etaTo,
            LocalDateTime ataFrom,
            LocalDateTime ataTo,
            LocalDateTime etdFrom,
            LocalDateTime etdTo,
            LocalDateTime atdFrom,
            LocalDateTime atdTo,
            String lastVisitFlag,
            String operationType,
            List<String> terminalConditions
    );

    List<VesselVisitDto> findVesselVisitByCarrierName (
            Query query,
            String facilityId,
            String carrierName,
            String lastVisitFlag,
            String operationType
    );

    List<VesselVisitDto> findSimpleGlobalVesselVisit(
            Query query,
            String searchParam,
            String facilityId,
            String lastVisitFlag,
            String operationType
    );
}
