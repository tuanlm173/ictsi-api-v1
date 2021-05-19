package com.justanalytics.controller;

import com.justanalytics.dto.ContainerDto;
import com.justanalytics.dto.TruckVisitDto;
import com.justanalytics.dto.VesselVisitDto;
import com.justanalytics.exception.UnAccessibleSystemException;
import com.justanalytics.response.RestEnvelope;
import com.justanalytics.service.ContainerService;
import com.justanalytics.service.DataService;
import com.justanalytics.service.TruckVisitService;
import com.justanalytics.service.VesselVisitService;
import com.justanalytics.types.CtxPath;
import net.minidev.json.JSONObject;
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
            @RequestParam(value = "container-type") String containerType,
            @RequestParam(value = "container-number", required = false) String containerNumber,
            @RequestParam(value = "container-operation-line-id", required = false) String containerOperationLineId,
            @RequestParam(value = "arrive-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arriveFrom,
            @RequestParam(value = "arrive-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arriveTo,
            @RequestParam(value = "depart-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departFrom,
            @RequestParam(value = "depart-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departTo,
            @RequestParam(value = "container-freightkind", required = false) String containerFreightKind,
            @RequestParam(value = "container-visit-state", required = false) String containerVisitState,
            @RequestParam(value = "container-transit-state", required = false) String containerTransitState,
            @RequestParam(value = "container-equipment-type", required = false) String containerEquipmentType,
            @RequestParam(value = "container-iso-group", required = false) String containerIsoGroup,
            @RequestParam(value = "container-arrive-pos-loctype", required = false) String containerArrivePosLocType,
            @RequestParam(value = "container-depart-pos-loctype", required = false) String containerDepartPosLocType,
            @RequestParam(value = "container-depart-pos-locid", required = false) String containerDepartPosLocId,
            @RequestParam(value = "container-arrive-pos-locid", required = false) String containerArrivePosLocId,
            @RequestParam(value = "container-booking-number", required = false) String containerBookingNumber,
            @RequestParam(value = "bol-number", required = false) String bolNumber,
            @RequestParam(value = "imped-type", required = false) String impedType,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size
    ) {
        if (dataService.checkAccessv3(productId, apiId)) {
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
                    size
            );
            return ResponseEntity.ok()
                    .header("row-count", "" + containers.size())
                    .body(RestEnvelope.of(containers));
        }
        throw new UnAccessibleSystemException();

    }

    @GetMapping(path = "/api/v1/getVesselVisitDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getVesselVisitDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
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
        if (dataService.checkAccessv3(productId, apiId)) {
            List<VesselVisitDto> vesselVisits = vesselVisitService.findVesselVisit(
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
                    size
            );
            return ResponseEntity.ok()
                    .header("row-count", "" + vesselVisits.size())
                    .body(RestEnvelope.of(vesselVisits));

        }
        throw new UnAccessibleSystemException();
    }


    @GetMapping(path = "/api/v1/getTruckVisitDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestEnvelope> getTruckVisitDetails(
            @RequestHeader(PRODUCT_ID_HEADER) String productId,
            @RequestHeader(API_ID_HEADER) String apiId,
            @RequestParam(value = "truck-license-number", required = false) String truckLicenseNbr,
            @RequestParam(value = "move-kind", required = false) String moveKind,
            @RequestParam(value = "visit-time-from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeFrom,
            @RequestParam(value = "visit-time-to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTimeTo,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size
    ) {

        if (dataService.checkAccessv3(productId, apiId)) {
            List<TruckVisitDto> truckVisits = truckVisitService.findTruckVisit(
                    truckLicenseNbr,
                    moveKind,
                    visitTimeFrom,
                    visitTimeTo,
                    size
            );
            return ResponseEntity.ok()
                    .header("row-count", "" + truckVisits.size())
                    .body(RestEnvelope.of(truckVisits));

        }
        throw new UnAccessibleSystemException();

    }

}
