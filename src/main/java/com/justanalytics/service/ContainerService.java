package com.justanalytics.service;

import com.justanalytics.dto.ContainerDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ContainerService {

    List<ContainerDto> findContainer(
            String containerType,
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
            String impedType,
            String size
    );

    List<ContainerDto> findEmptyContainer(
            String containerType,
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
            String impedType,
            String size
    );
}
