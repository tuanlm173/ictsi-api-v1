package com.justanalytics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.justanalytics.dto.*;
import com.justanalytics.exception.InvalidParameterException;
import com.justanalytics.exception.UnAccessibleSystemException;
import com.justanalytics.query.Query;
import com.justanalytics.response.RestEnvelope;
import com.justanalytics.service.*;
import com.justanalytics.types.ContainerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
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
    private FacilityService facilityService;

    @Autowired
    private VesselEventService vesselEventService;

    @Autowired
    private ContainerEventService containerEventService;

    @Autowired
    private TruckEventService truckEventService;

    @Autowired
    private TruckingTransactionsService truckingTransactionsService;

    @Autowired
    private DataService dataService;

    @PostMapping(path = "/api/v1/getContainerDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getContainerDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "container-type") String containerType,
            @RequestParam(value = "facility-id", required = false) String facilityId,
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
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            if (ContainerType.IMPORT.getContainerType().equalsIgnoreCase(containerType) || ContainerType.ALL.getContainerType().equalsIgnoreCase(containerType)) {
                List<ContainerDto> containers = containerService.findContainer(
                        query,
                        containerType,
                        facilityId,
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
                        operationType,
                        terminalConditions
                );
                return ResponseEntity.ok()
                        .header("row-count", "" + containers.size())
                        .body(RestEnvelope.of(containers));
            }
            else if (ContainerType.EXPORT.getContainerType().equalsIgnoreCase(containerType)) {
                List<ExportContainerDto> exportContainer = containerService.findExportContainer(
                        query,
                        containerType,
                        facilityId,
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
                        operationType,
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

    @PostMapping(path = "/api/v1/getVesselVisitDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getVesselVisitDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "facility-id", required = false) String facilityId,
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
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) throws JsonProcessingException {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            List<VesselVisitDto> vesselVisits = vesselVisitService.findVesselVisit(
                    query, facilityId, carrierName, carrierOperatorId, carrierVisitId, serviceId, visitPhase,
                    etaFrom, etaTo, ataFrom, ataTo, etdFrom, etdTo, atdFrom, atdTo, operationType, terminalConditions);
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
            @RequestParam(value = "facility-id", required = false) String facilityId,
            @RequestParam(value = "truck-license-number", required = false) String truckLicenseNbrs,
            @RequestParam(value = "visit-phase", required = false) String visitPhases,
            @RequestParam(value = "carrier-operator-name", required = false) String carrierOperatorNames,
            @RequestParam(value = "visit-time-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeFrom,
            @RequestParam(value = "visit-time-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeTo,
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            List<TruckVisitDto> truckVisits = truckVisitService.findTruckVisit(query, facilityId, truckLicenseNbrs,
                    visitPhases, carrierOperatorNames, visitTimeFrom, visitTimeTo, operationType, terminalConditions);
            return ResponseEntity.ok()
                    .header("row-count", "" + truckVisits.size())
                    .body(RestEnvelope.of(truckVisits));
        }
        throw new UnAccessibleSystemException();

    }

    @PostMapping(path = "/api/v1/getFacility", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getFacility(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
        List<FacilityDto> facilities = facilityService.findFacility(query);
        return ResponseEntity.ok()
                .header("row-count", "" + facilities.size())
                .body(RestEnvelope.of(facilities));
        }
        throw new UnAccessibleSystemException();
    }

    @PostMapping(path = "/api/v1/getVesselEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getVesselEvent(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "unique-key", required = false) String uniqueKey,
            @RequestParam(value = "language", required = false, defaultValue = "en-us") String language,
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<VesselEventDto> vesselEvents = vesselEventService.findVesselEvent(uniqueKey, language, operationType, query);
            return ResponseEntity.ok()
                    .header("row-count", "" + vesselEvents.size())
                    .body(RestEnvelope.of(vesselEvents));
        }
        throw new UnAccessibleSystemException();
    }

    @PostMapping(path = "/api/v1/getContainerEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getContainerEvent(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "unique-key", required = false) String uniqueKey,
            @RequestParam(value = "language", required = false, defaultValue = "en-us") String language,
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<ContainerEventDto> containerEvents = containerEventService.findContainerEvent(uniqueKey, language, operationType, query);
            return ResponseEntity.ok()
                    .header("row-count", "" + containerEvents.size())
                    .body(RestEnvelope.of(containerEvents));
        }
        throw new UnAccessibleSystemException();
    }

    @PostMapping(path = "/api/v1/getTruckEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getTruckEvent(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "unique-key", required = false) String uniqueKey,
            @RequestParam(value = "language", required = false, defaultValue = "en-us") String language,
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<TruckEventDto> truckEvent = truckEventService.findTruckEvent(uniqueKey, language, operationType, query);
            return ResponseEntity.ok()
                    .header("row-count", "" + truckEvent.size())
                    .body(RestEnvelope.of(truckEvent));
        }
        throw new UnAccessibleSystemException();
    }

    @PostMapping(path = "/api/v1/getTruckTransactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getTruckTransactions(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestHeader(SUBSCRIPTION_ID_HEADER) String subscriptionId,
            @RequestParam(value = "truck-company", required = false) String truckCompany,
            @RequestParam(value = "truck-plate", required = false) String truckPlate,
            @RequestParam(value = "unique-key", required = false) String uniqueKey,
            @RequestParam(value = "truck-visit-time-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeFrom,
            @RequestParam(value = "truck-visit-time-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeTo,
            @RequestParam(value = "operation-type", required = false, defaultValue = "AND") String operationType,
            @RequestBody Query query
    ) {
        if (dataService.checkAccessFromCosmos(productId, apiId, subscriptionId)) {
            List<String> terminalConditions = dataService.findCondition(productId, apiId, subscriptionId);
            List<TruckTransactionsDto> truckTransactions = truckingTransactionsService.findTruckTransactions(
                    query, truckCompany, truckPlate, uniqueKey, visitTimeFrom, visitTimeTo, operationType, terminalConditions);
            return ResponseEntity.ok()
                    .header("row-count", "" + truckTransactions.size())
                    .body(RestEnvelope.of(truckTransactions));
        }
        throw new UnAccessibleSystemException();
    }

}
