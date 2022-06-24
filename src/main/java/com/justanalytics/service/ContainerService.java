package com.justanalytics.service;

import com.justanalytics.dto.ContainerDto;
import com.justanalytics.dto.EmptyContainerDto;
import com.justanalytics.dto.ExportContainerDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ContainerService {

    List<ContainerDto> findContainer(
            Query query,
            String containerType,
            String facilityId,
            String containerNumber,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerEquipmentType,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerBookingNumber,
            String bolNumber,
            String containerUniqueKey,
            String shipper,
            String msBooking,
            String msBillOfLading,
            String msUniqueKey,
            String lastVisitFlag,
            String impedType,
            String operationType,
            List<String> terminalConditions
    );

    List<ExportContainerDto> findExportContainer(
            Query query,
            String containerType,
            String facilityId,
            String containerNumber,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerEquipmentType,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerBookingNumber,
            String bolNumber,
            String containerUniqueKey,
            String shipper,
            String msBooking,
            String msBillOfLading,
            String msUniqueKey,
            String lastVisitFlag,
            String impedType,
            String operationType,
            List<String> terminalConditions
    );


    List<EmptyContainerDto> findEmptyContainer(
            Query query,
            String containerType,
            String facilityId,
            String containerNumber,
            String containerOperationLineId,
            String containerVisitState,
            String containerTransitState,
            String containerEquipmentType,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerUniqueKey,
            String bolNumber,
            String bookingNumber,
            String lastVisitFlag,
            String impedType,
            String operationType,
            List<String> terminalConditions
    );

    List<ContainerDto> findCommonContainer(
            Query query,
            String facilityId,
            String containerNumber,
            String containerBookingNumber,
            String bolNumber,
            String lastVisitFlag,
            String operationType
    );

    List<ContainerDto> findSimpleGlobalContainer(
            Query query,
            String searchParam,
            String facilityId,
            String msBooking,
            String msBillOfLading,
            String msUniqueKey,
            String lastVisitFlag,
            String operationType
    );

}
