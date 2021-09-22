package com.justanalytics.service;

import com.justanalytics.dto.VesselVisitDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface VesselVisitService {

    List<VesselVisitDto> findVesselVisit(
            String carrierName,
            String carrierOperatorId,
            String carrierVisitId,
            String serviceId,
            String visitPhase,
            LocalDateTime etaFrom,
            LocalDateTime etaTo,
            LocalDateTime ataFrom,
            LocalDateTime ataTo,
            LocalDateTime etdFrom,
            LocalDateTime etdTo,
            LocalDateTime atdFrom,
            LocalDateTime atdTo,
            String size,
            List<String> terminalConditions
    );

    List<VesselVisitDto> findVesselVisitV2(
            Query query,
            String carrierName,
            String carrierOperatorId,
            String carrierVisitId,
            String serviceId,
            String visitPhase,
            LocalDateTime etaFrom,
            LocalDateTime etaTo,
            LocalDateTime ataFrom,
            LocalDateTime ataTo,
            LocalDateTime etdFrom,
            LocalDateTime etdTo,
            LocalDateTime atdFrom,
            LocalDateTime atdTo,
            String operationType,
            List<String> terminalConditions
    );
}
