package com.justanalytics.service;

import com.justanalytics.dto.ContainerVesselTruckDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonService {

    ContainerVesselTruckDto findCombinedEntity(
            Query query,
            String facilityId,
            String containerNumber,
            String containerBookingNumber,
            String bolNumber,
            String carrierName,
            String visitPhases,
            String lastVisitFlag,
            String operationType
    );
}
