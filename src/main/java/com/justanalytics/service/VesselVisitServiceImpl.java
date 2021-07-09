package com.justanalytics.service;

import com.justanalytics.dto.VesselVisitDto;
import com.justanalytics.repository.DataRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.justanalytics.constant.VesselVisitBaseCondition.*;

@Service
public class VesselVisitServiceImpl implements VesselVisitService {

    Logger logger = LoggerFactory.getLogger(VesselVisitServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private DataRepository dataRepository;


    private StringBuilder buildSimpleVesselVisitQuery(String query, String size) {
        StringBuilder queryBuilder = new StringBuilder();
        return queryBuilder.append(String.format(query, size));
    }

    private String buildSimpleVesselParam(String filter, String inputVesselVisitFields) {
        if (inputVesselVisitFields != null && !inputVesselVisitFields.isBlank()) {
            String[] vesselVisits = inputVesselVisitFields.split(",");
            List<String> results = new ArrayList<>();
            for (String vesselVisit: vesselVisits) {
                results.add(String.format(filter, vesselVisit));
            }
            return "(" + String.join(" OR ", results) + ")";
        }
        else return DEFAULT_CONDITION;
    }

    private String buildSimpleTimeframeVesselParam(String filter, LocalDateTime from, LocalDateTime to) {
        if ((from != null && !from.toString().isBlank()) && (to != null && !to.toString().isBlank())) {
            return String.format(filter, from.format(iso_formatter) + 'Z', to.format(iso_formatter) + 'Z');
        }
            return DEFAULT_CONDITION;
    }

    private List<VesselVisitDto> getVesselVisitDto(List<JSONObject> rawData) {

        List<VesselVisitDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data : rawData) {
            String uniqueKey = String.valueOf(data.get("UniqueKey"));
            String terminalOperatorId = String.valueOf(data.get("Terminal_Operator_ID"));
            String complexId = String.valueOf(data.get("Complex_ID"));
            String facilityId = String.valueOf(data.get("Facility_ID"));
            String carrierVisitId = String.valueOf(data.get("Carrier_Visit_ID"));
            String carrierName = String.valueOf(data.get("Carrier_Name"));
            String lloydsId = String.valueOf(data.get("Lloyds_ID"));
            String carrierMode = String.valueOf(data.get("Carrier_Mode"));
            String visitNbr = String.valueOf(data.get("Visit_Nbr"));
            String visitPhase = String.valueOf(data.get("Visit_Phase"));
            String carrierOperatorId = String.valueOf(data.get("Carrier_Operator_ID"));
            String carrierOperatorName = String.valueOf(data.get("Carrier_Operator_Name"));
            String eta = String.valueOf(data.get("ETA"));
            String ata = String.valueOf(data.get("ATA"));
            String etd = String.valueOf(data.get("ETD"));
            String atd = String.valueOf(data.get("ATD"));
            String beginReceive = String.valueOf(data.get("Begin_Receive"));
            String cargoCutoff = String.valueOf(data.get("Cargo_Cutoff"));
            String hazCutoff = String.valueOf(data.get("Haz_Cutoff"));
            String reeferCutoff = String.valueOf(data.get("Reefer_Cutoff"));
            String laborOnboard = String.valueOf(data.get("Labor_Onboard"));
            String laborOffboard = String.valueOf(data.get("Labor_Offboard"));
            String arrivalOffPort = String.valueOf(data.get("Arrival_Off_Port"));
            String departureOffPort = String.valueOf(data.get("Departure_Off_Port"));
            String pilotOnboard = String.valueOf(data.get("Pilot_Onboard"));
            String pilotOffboard = String.valueOf(data.get("Pilot_Offboard"));
            String startWork = String.valueOf(data.get("Start_work"));
            String endWork = String.valueOf(data.get("End_Work"));
            String classification = String.valueOf(data.get("Classification"));
            Integer estimatedLoadMoves = Objects.nonNull(data.get("Estimated_Load_Moves")) ? Integer.parseInt(String.valueOf(data.get("Estimated_Load_Moves"))) : null;
            Integer estimatedDischargeMoves = Objects.nonNull(data.get("Estimated_Discharge_Moves")) ? Integer.parseInt(String.valueOf(data.get("Estimated_Discharge_Moves"))) : null;
            Integer estimatedRestowMoves = Objects.nonNull(data.get("Estimated_Restow_Moves")) ? Integer.parseInt(String.valueOf(data.get("Estimated_Restow_Moves"))) : null;
            Integer estimatedShiftOnboardMoves = Objects.nonNull(data.get("Estimated_Shift_Onboard_Moves")) ? Integer.parseInt(String.valueOf(data.get("Estimated_Shift_Onboard_Moves"))) : null;
            Integer estimatedBreakbulkLoadMoves = Objects.nonNull(data.get("Estimated_Breakbulk_Load_Moves")) ? Integer.parseInt(String.valueOf(data.get("Estimated_Breakbulk_Load_Moves"))) : null;
            Integer estimatedBreakbulkDischargeMoves = Objects.nonNull(data.get("Estimated_Breakbulk_Discharge_Moves")) ? Integer.parseInt(String.valueOf(data.get("Estimated_Breakbulk_Discharge_Moves"))) : null;
            String countryCode = String.valueOf(data.get("CountryCode"));
            String flexString01 = String.valueOf(data.get("flex_string01"));
            String flexString02 = String.valueOf(data.get("flex_string02"));
            String flexString03 = String.valueOf(data.get("flex_string03"));
            String ibVyg = String.valueOf(data.get("ib_vyg"));
            String obVyg = String.valueOf(data.get("ob_vyg"));
            String quayId = String.valueOf(data.get("Quay_ID"));
            String quayName = String.valueOf(data.get("Quay_Name"));
            String serviceId = String.valueOf(data.get("service_ID"));
            String serviceName = String.valueOf(data.get("service_Name"));

            results.add(VesselVisitDto.builder()
                    .uniqueKey(uniqueKey)
                    .terminalOperatorId(terminalOperatorId)
                    .complexId(complexId)
                    .facilityId(facilityId)
                    .carrierVisitId(carrierVisitId)
                    .carrierName(carrierName)
                    .lloydsId(lloydsId)
                    .carrierMode(carrierMode)
                    .visitNbr(visitNbr)
                    .visitPhase(visitPhase)
                    .carrierOperatorId(carrierOperatorId)
                    .carrierOperatorName(carrierOperatorName)
                    .eta(eta)
                    .ata(ata)
                    .etd(etd)
                    .atd(atd)
                    .beginReceive(beginReceive)
                    .cargoCutoff(cargoCutoff)
                    .hazCutoff(hazCutoff)
                    .reeferCutoff(reeferCutoff)
                    .laborOnboard(laborOnboard)
                    .laborOffboard(laborOffboard)
                    .arrivalOffPort(arrivalOffPort)
                    .departureOffPort(departureOffPort)
                    .pilotOnboard(pilotOnboard)
                    .pilotOffboard(pilotOffboard)
                    .startWork(startWork)
                    .endWork(endWork)
                    .classification(classification)
                    .estimatedLoadMoves(estimatedLoadMoves)
                    .estimatedDischargeMoves(estimatedDischargeMoves)
                    .estimatedRestowMoves(estimatedRestowMoves)
                    .estimatedShiftOnboardMoves(estimatedShiftOnboardMoves)
                    .estimatedBreakbulkLoadMoves(estimatedBreakbulkLoadMoves)
                    .estimatedBreakbulkDischargeMoves(estimatedBreakbulkDischargeMoves)
                    .countryCode(countryCode)
                    .flexString01(flexString01)
                    .flexString02(flexString02)
                    .flexString03(flexString03)
                    .ibVyg(ibVyg)
                    .obVyg(obVyg)
                    .quayId(quayId)
                    .quayName(quayName)
                    .serviceId(serviceId)
                    .serviceName(serviceName)
                    .build());

        }

        return results;
    }

    @Override
    public List<VesselVisitDto> findVesselVisit(
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
    ) {
        List<String> filters = new ArrayList<>();

        StringBuilder queryBuilder = buildSimpleVesselVisitQuery(VESSEL_VISIT_BASE_QUERY, size);

        String vesselVisitCarrierOperatorIdFilter = buildSimpleVesselParam(CARRIER_OPERATOR_ID, carrierOperatorId);
        String vesselVisitCarrierVisitIdFilter = buildSimpleVesselParam(CARRIER_VISIT_ID, carrierVisitId);
        String vesselVisitServiceIdFilter = buildSimpleVesselParam(SERVICE_ID, serviceId);
        String vesselVisitPhaseFilter = buildSimpleVesselParam(VISIT_PHASE, visitPhase);

        String vesselVisitEtaFilter = buildSimpleTimeframeVesselParam(ETA, etaFrom, etaTo);
        String vesselVisitAtaFilter = buildSimpleTimeframeVesselParam(ATA, ataFrom, ataTo);
        String vesselVisitEtdFilter = buildSimpleTimeframeVesselParam(ETD, etdFrom, etdTo);
        String vesselVisitAtdFilter = buildSimpleTimeframeVesselParam(ATD, atdFrom, atdTo);

        filters.add(vesselVisitCarrierOperatorIdFilter);
        filters.add(vesselVisitCarrierVisitIdFilter);
        filters.add(vesselVisitServiceIdFilter);
        filters.add(vesselVisitPhaseFilter);
        filters.add(vesselVisitEtaFilter);
        filters.add(vesselVisitAtaFilter);
        filters.add(vesselVisitEtdFilter);
        filters.add(vesselVisitAtdFilter);

        filters = filters.stream().filter(e -> !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

        if (filters.size() == 0) {
            queryBuilder.append("");
        } else {
            queryBuilder.append(String.format(" AND %s", String.join(" AND ", filters)));
        }

        if(!terminalConditions.contains("ALL")) {
            queryBuilder.append(" AND ");
            List<String> conditions = new ArrayList<>();
            for (String terminalCondition : terminalConditions) {
                conditions.add(String.format("c.Facility_ID = '%s'", terminalCondition));
            }
            queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
        }

        if (size.equalsIgnoreCase("1")) {
            queryBuilder.append(" ORDER BY c.ETA DESC");
        }
        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        return getVesselVisitDto(rawData);

    }
}
