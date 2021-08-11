package com.justanalytics.controller;

import com.justanalytics.dto.*;
import com.justanalytics.exception.InvalidParameterException;
import com.justanalytics.exception.UnAccessibleSystemException;
import com.justanalytics.query.Query;
import com.justanalytics.response.RestEnvelope;
import com.justanalytics.service.ContainerService;
import com.justanalytics.service.DataService;
import com.justanalytics.service.TruckVisitService;
import com.justanalytics.service.VesselVisitService;
import com.justanalytics.types.ContainerType;
import com.justanalytics.types.CtxPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping({CtxPath.INTERNAL, CtxPath.EXTERNAL})
public class DataController {

    private static final String PRODUCT_ID_HEADER = "x-request-product-id";
    private static final String API_ID_HEADER = "x-api-id";
    private static final String SUBSCRIPTION_ID_HEADER = "x-subscription-id";

    @Autowired
    private ContainerService containerService;

    @Autowired
    private VesselVisitService vesselVisitService;

    @Autowired
    private TruckVisitService truckVisitService;

    @Autowired
    private DataService dataService;

    @GetMapping(path = "/api/v1/getContainerDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getContainerDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "container-type") String containerType,
            @RequestParam(value = "container-number", required = false) String containerNumber,
            @RequestParam(value = "operation-line-id", required = false) String containerOperationLineId,
            @RequestParam(value = "arrive-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arriveFrom,
            @RequestParam(value = "arrive-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arriveTo,
            @RequestParam(value = "depart-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departFrom,
            @RequestParam(value = "depart-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departTo,
            @RequestParam(value = "freightkind", required = false) String containerFreightKind,
            @RequestParam(value = "visit-state", required = false) String containerVisitState,
            @RequestParam(value = "transit-state", required = false) String containerTransitState,
            @RequestParam(value = "equipment-type", required = false) String containerEquipmentType,
            @RequestParam(value = "iso-group", required = false) String containerIsoGroup,
            @RequestParam(value = "arrive-pos-loctype", required = false) String containerArrivePosLocType,
            @RequestParam(value = "depart-pos-loctype", required = false) String containerDepartPosLocType,
            @RequestParam(value = "depart-pos-locid", required = false) String containerDepartPosLocId,
            @RequestParam(value = "arrive-pos-locid", required = false) String containerArrivePosLocId,
            @RequestParam(value = "booking-number", required = false) String containerBookingNumber,
            @RequestParam(value = "bol-number", required = false) String bolNumber,
            @RequestParam(value = "imped-type", required = false) String impedType,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size
    ) {
        if (dataService.checkAccessv2(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            if (ContainerType.IMPORT.getContainerType().equalsIgnoreCase(containerType) || ContainerType.ALL.getContainerType().equalsIgnoreCase(containerType)) {
                List<ContainerDto> containers = containerService.findContainer(
                        containerType,
                        containerNumber,
                        containerOperationLineId,
                        arriveFrom,
                        arriveTo,
                        departFrom,
                        departTo,
                        containerFreightKind,
                        containerVisitState,
                        containerTransitState,
                        containerEquipmentType,
                        containerIsoGroup,
                        containerArrivePosLocType,
                        containerDepartPosLocType,
                        containerDepartPosLocId,
                        containerArrivePosLocId,
                        containerBookingNumber,
                        bolNumber,
                        impedType,
                        size,
                        terminalConditions
                );
                return ResponseEntity.ok()
                        .header("row-count", "" + containers.size())
                        .body(RestEnvelope.of(containers));
            }
            else if (ContainerType.EMPTY.getContainerType().equalsIgnoreCase(containerType)) {
                List<EmptyContainerDto> emptyContainer = containerService.findEmptyContainer(
                        containerType,
                        containerNumber,
                        containerOperationLineId,
                        arriveFrom,
                        arriveTo,
                        departFrom,
                        departTo,
                        containerFreightKind,
                        containerVisitState,
                        containerTransitState,
                        containerEquipmentType,
                        containerIsoGroup,
                        containerArrivePosLocType,
                        containerDepartPosLocType,
                        containerDepartPosLocId,
                        containerArrivePosLocId,
                        containerBookingNumber,
                        bolNumber,
                        impedType,
                        size,
                        terminalConditions
                );
                return ResponseEntity.ok()
                        .header("row-count", "" + emptyContainer.size())
                        .body(RestEnvelope.of(emptyContainer));
            }
            else if (ContainerType.EXPORT.getContainerType().equalsIgnoreCase(containerType)) {
                List<ExportContainerDto> exportContainer = containerService.findExportContainer(
                        containerType,
                        containerNumber,
                        containerOperationLineId,
                        arriveFrom,
                        arriveTo,
                        departFrom,
                        departTo,
                        containerFreightKind,
                        containerVisitState,
                        containerTransitState,
                        containerEquipmentType,
                        containerIsoGroup,
                        containerArrivePosLocType,
                        containerDepartPosLocType,
                        containerDepartPosLocId,
                        containerArrivePosLocId,
                        containerBookingNumber,
                        bolNumber,
                        impedType,
                        size,
                        terminalConditions
                );
                return ResponseEntity.ok()
                        .header("row-count", "" + exportContainer.size())
                        .body(RestEnvelope.of(exportContainer));
            }
            throw new InvalidParameterException("container type must be empty, export, import, all");
        }
        throw new UnAccessibleSystemException();

    }

    @GetMapping(path = "/api/v1/getVesselVisitDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getVesselVisitDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "carrier-name", required = false) String carrierName,
            @RequestParam(value = "carrier-operator-id", required = false) String carrierOperatorId,
            @RequestParam(value = "carrier-visit-id", required = false) String carrierVisitId,
            @RequestParam(value = "service-id", required = false) String serviceId,
            @RequestParam(value = "visit-phase", required = false) String visitPhase,
            @RequestParam(value = "eta-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime etaFrom,
            @RequestParam(value = "eta-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime etaTo,
            @RequestParam(value = "ata-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ataFrom,
            @RequestParam(value = "ata-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ataTo,
            @RequestParam(value = "etd-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime etdFrom,
            @RequestParam(value = "etd-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime etdTo,
            @RequestParam(value = "atd-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime atdFrom,
            @RequestParam(value = "atd-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime atdTo,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size
    ) {
        if (dataService.checkAccessv2(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            List<VesselVisitDto> vesselVisits = vesselVisitService.findVesselVisit(
                    carrierName,
                    carrierOperatorId,
                    carrierVisitId,
                    serviceId,
                    visitPhase,
                    etaFrom,
                    etaTo,
                    ataFrom,
                    ataTo,
                    etdFrom,
                    etdTo,
                    atdFrom,
                    atdTo,
                    size,
                    terminalConditions
            );
            return ResponseEntity.ok()
                    .header("row-count", "" + vesselVisits.size())
                    .body(RestEnvelope.of(vesselVisits));

        }
        throw new UnAccessibleSystemException();
    }

    @PostMapping(path = "/api/v1/getTruckVisitDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getTruckVisitDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "truck-license-number", required = false) String truckLicenseNbrs,
            @RequestParam(value = "move-kind", required = false) String moveKinds,
            @RequestParam(value = "visit-time-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeFrom,
            @RequestParam(value = "visit-time-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeTo,
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestParam(value = "order-by", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size,
            @RequestParam(name = "format", required = false, defaultValue = "json") String format,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessv2(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            List<TruckVisitDto> truckVisits = truckVisitService.findTruckVisitV3(query, truckLicenseNbrs, moveKinds, size, operationType, terminalConditions);
            return ResponseEntity.ok()
                    .header("row-count", "" + truckVisits.size())
                    .body(RestEnvelope.of(truckVisits));
        }
        throw new UnAccessibleSystemException();

    }

}
