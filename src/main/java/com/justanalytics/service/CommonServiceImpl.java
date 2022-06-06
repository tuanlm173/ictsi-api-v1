package com.justanalytics.service;

import com.justanalytics.dto.ContainerDto;
import com.justanalytics.dto.ContainerVesselTruckDto;
import com.justanalytics.dto.TruckVisitDto;
import com.justanalytics.dto.VesselVisitDto;
import com.justanalytics.query.Query;
import com.justanalytics.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.justanalytics.constant.ContainerBaseCondition.ALL_CONTAINER_BASE_QUERY;

@Service
public class CommonServiceImpl implements CommonService {

    Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TruckVisitService truckVisitService;

    @Autowired
    private VesselVisitService vesselVisitService;

    @Autowired
    private ContainerService containerService;


    private ContainerVesselTruckDto getCombinedEntityDto(List<ContainerDto> containers, List<VesselVisitDto> vesselVisits, List<TruckVisitDto> truckVisits) {

        return ContainerVesselTruckDto.builder().containerDto(containers).vesselVisitDto(vesselVisits).truckVisitDto(truckVisits).build();
    }

    @Override
    public ContainerVesselTruckDto findCombinedEntity(
            Query query,
            String facilityId,
            String containerNumber,
            String containerBookingNumber,
            String bolNumber,
            String carrierName,
            String visitPhases,
            String lastVisitFlag,
            String operationType
    ) {

        List<ContainerDto> containers = containerService.findCommonContainer(query, facilityId, containerNumber, containerBookingNumber, bolNumber, lastVisitFlag, operationType);
        List<VesselVisitDto> vesselVisits = vesselVisitService.findVesselVisitByCarrierName(query, facilityId, carrierName, lastVisitFlag, operationType);
        List<TruckVisitDto> truckVisits = truckVisitService.findTruckVisitByTruckPlate(query, visitPhases, lastVisitFlag, operationType);

        return getCombinedEntityDto(containers, vesselVisits, truckVisits);
    }

    @Override
    public ContainerVesselTruckDto findSimpleCombinedGlobalEntity(
            Query query,
            String searchParam,
            String facilityId,
            String msBooking,
            String msBillOfLading,
            String msUniqueKey,
            String lastVisitFlag,
            String operationType
    ) {

        List<ContainerDto> containers = containerService.findSimpleGlobalContainer(query, searchParam, facilityId, msBooking, msBillOfLading, msUniqueKey, lastVisitFlag, operationType);
        List<VesselVisitDto> vesselVisits = vesselVisitService.findSimpleGlobalVesselVisit(query, searchParam, facilityId, lastVisitFlag, operationType);
        List<TruckVisitDto> truckVisits = truckVisitService.findSimpleGlobalTruckVisit(query, searchParam, facilityId, lastVisitFlag, operationType);
        return getCombinedEntityDto(containers, vesselVisits, truckVisits);
    }
}
