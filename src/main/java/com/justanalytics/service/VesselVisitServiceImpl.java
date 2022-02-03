package com.justanalytics.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justanalytics.dto.LanguageDescription;
import com.justanalytics.dto.VesselVisitDto;
import com.justanalytics.query.Query;
import com.justanalytics.repository.DataRepository;
import com.justanalytics.utils.QueryBuilder;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.justanalytics.constant.TruckVisitBaseCondition.DEFAULT_CONDITION;
import static com.justanalytics.constant.VesselVisitBaseCondition.*;

@Service
public class VesselVisitServiceImpl implements VesselVisitService {

    Logger logger = LoggerFactory.getLogger(VesselVisitServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String currentTime = "'" + LocalDateTime.now().minusDays(180).format(localDateTimeFormatter) + "Z'"; // DEV: 90 PROD: 45

    @Autowired
    private DataRepository dataRepository;

    private String filterLastVisitFlag(String lastVisitFlag) {
        String results = "c.last_visit_flag != 1";
        if (lastVisitFlag != null && !lastVisitFlag.isBlank()) {
            if (lastVisitFlag.equalsIgnoreCase("true")) {
                results = "c.last_visit_flag = 1";
            }
        }
        return results;
    }


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

    private String parseParams(String params) {
        if (params != null && !params.isBlank())
            return String.join(", ",
                    Arrays.stream(params.split(","))
                            .map(element -> ("'" + element + "'"))
                            .collect(Collectors.toList()));
        else return "";

    }

    private String buildFilter(String filter, String input) {
        if (input != null && !input.isBlank())
            return String.format(filter, input);
        else return "";
    }

    private List<VesselVisitDto> getVesselVisitDto(List<JSONObject> rawData) throws JsonProcessingException {

        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        ObjectMapper mapper = new ObjectMapper(factory);

        List<VesselVisitDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data : rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String terminalOperatorId = String.valueOf(data.get("terminal_operator_id"));
            String complexId = String.valueOf(data.get("complex_id"));
            String facilityId = String.valueOf(data.get("facility_id"));
            String carrierVisitId = String.valueOf(data.get("carrier_visit_id"));
            String carrierName = String.valueOf(data.get("carrier_name"));
            String lloydsId = String.valueOf(data.get("lloyds_id"));
            String carrierMode = String.valueOf(data.get("carrier_mode"));
            String visitNbr = String.valueOf(data.get("visit_nbr"));
            String visitPhase = String.valueOf(data.get("visit_phase"));
            String carrierOperatorId = String.valueOf(data.get("carrier_operator_id"));
            String carrierOperatorName = String.valueOf(data.get("carrier_operator_name"));
            String eta = String.valueOf(data.get("eta"));
            String ata = String.valueOf(data.get("ata"));
            String etd = String.valueOf(data.get("etd"));
            String atd = String.valueOf(data.get("atd"));
            String beginReceive = String.valueOf(data.get("begin_receive"));
            String cargoCutoff = String.valueOf(data.get("cargo_cutoff"));
            String hazCutoff = String.valueOf(data.get("haz_cutoff"));
            String reeferCutoff = String.valueOf(data.get("reefer_cutoff"));
            String laborOnboard = String.valueOf(data.get("labor_onboard"));
            String laborOffboard = String.valueOf(data.get("labor_offboard"));
            String arrivalOffPort = String.valueOf(data.get("arrival_off_port"));
            String departureOffPort = String.valueOf(data.get("departure_off_port"));
            String pilotOnboard = String.valueOf(data.get("pilot_onboard"));
            String pilotOffboard = String.valueOf(data.get("pilot_offboard"));
            String startWork = String.valueOf(data.get("start_work"));
            String endWork = String.valueOf(data.get("end_work"));
            String classification = String.valueOf(data.get("classification"));
            Integer estimatedLoadMoves = Objects.nonNull(data.get("estimated_load_moves")) ? Integer.parseInt(String.valueOf(data.get("estimated_load_moves"))) : null;
            Integer estimatedDischargeMoves = Objects.nonNull(data.get("estimated_discharge_moves")) ? Integer.parseInt(String.valueOf(data.get("estimated_discharge_moves"))) : null;
            Integer estimatedRestowMoves = Objects.nonNull(data.get("estimated_restow_moves")) ? Integer.parseInt(String.valueOf(data.get("estimated_restow_moves"))) : null;
            Integer estimatedShiftOnboardMoves = Objects.nonNull(data.get("estimated_shift_onboard_moves")) ? Integer.parseInt(String.valueOf(data.get("estimated_shift_onboard_moves"))) : null;
            Integer estimatedBreakbulkLoadMoves = Objects.nonNull(data.get("estimated_breakbulk_load_moves")) ? Integer.parseInt(String.valueOf(data.get("estimated_breakbulk_load_moves"))) : null;
            Integer estimatedBreakbulkDischargeMoves = Objects.nonNull(data.get("estimated_breakbulk_discharge_moves")) ? Integer.parseInt(String.valueOf(data.get("estimated_breakbulk_discharge_moves"))) : null;
            String countryCode = String.valueOf(data.get("countrycode"));
            String visitDetailsGkey = String.valueOf(data.get("visit_details_gkey"));
            String flexString01 = String.valueOf(data.get("flex_string01"));
            String flexString02 = String.valueOf(data.get("flex_string02"));
            String flexString03 = String.valueOf(data.get("flex_string03"));
            String ibVyg = String.valueOf(data.get("ib_vyg"));
            String obVyg = String.valueOf(data.get("ob_vyg"));
            String quayId = String.valueOf(data.get("quay_id"));
            String quayName = String.valueOf(data.get("quay_name"));
            String serviceId = String.valueOf(data.get("service_id"));
            String serviceName = String.valueOf(data.get("service_name"));
            String remarks = String.valueOf(data.get("remarks"));
            String estTimeOfCompletion = String.valueOf(data.get("est_time_of_completion"));
            String initialTimeOfCompletion = String.valueOf(data.get("initial_time_of_completion"));
            String amendedEstTimeOfCompletion = String.valueOf(data.get("amended_est_time_of_completion"));
            String estimatedTimeOfBerthing = String.valueOf(data.get("estimated_time_of_berthing"));
            String actualTimeOfBerthing = String.valueOf(data.get("actual_time_of_berthing"));
            String loadingCutoff = String.valueOf(data.get("loading_cutoff"));
            String exportCutoff = String.valueOf(data.get("export_cutoff"));
            String etbLct = String.valueOf(data.get("etb_lct"));
            String vesselRegistryNumber = String.valueOf(data.get("vessel_registry_number"));

            List<LanguageDescription> vesselStatus = new ArrayList<>();
            List<LanguageDescription> rawVesselStatus = (List<LanguageDescription>) data.get("vessel_statuses");
            if (rawVesselStatus != null) vesselStatus = rawVesselStatus;

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
                    .visitDetailsGkey(visitDetailsGkey)
                    .flexString01(flexString01)
                    .flexString02(flexString02)
                    .flexString03(flexString03)
                    .ibVyg(ibVyg)
                    .obVyg(obVyg)
                    .quayId(quayId)
                    .quayName(quayName)
                    .serviceId(serviceId)
                    .serviceName(serviceName)
                    .remarks(remarks)
                    .estTimeOfCompletion(estTimeOfCompletion)
                    .initialTimeOfCompletion(initialTimeOfCompletion)
                    .amendedEstTimeOfCompletion(amendedEstTimeOfCompletion)
                    .estimatedTimeOfBerthing(estimatedTimeOfBerthing)
                    .actualTimeOfBerthing(actualTimeOfBerthing)
                    .loadingCutoff(loadingCutoff)
                    .exportCutoff(exportCutoff)
                    .etbLct(etbLct)
                    .vesselRegistryNumber(vesselRegistryNumber)
                    .vesselStatus(vesselStatus)
                    .build());

        }

        return results;
    }

    @Override
    public List<VesselVisitDto> findVesselVisit(
            Query query,
            String facilityId,
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
            String lastVisitFlag,
            String operationType,
            List<String> terminalConditions
    ) throws JsonProcessingException {
        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(VESSEL_VISIT_BASE_QUERY, filterLastVisitFlag(lastVisitFlag), currentTime));

        // Persona filter
        List<String> personaFilters = new ArrayList<>();

        String personaFacilityId = buildFilter(FACILITY_ID, parseParams(facilityId));
        String personaCarrierName = buildFilter(CARRIER_NAME, parseParams(carrierName));
        String personaCarrierOperatorId = buildFilter(CARRIER_OPERATOR_ID, parseParams(carrierOperatorId));
        String personaCarrierVisitId = buildFilter(CARRIER_VISIT_ID, parseParams(carrierVisitId));
        String personaServiceId = buildFilter(SERVICE_ID, parseParams(serviceId));
        String personaVisitPhase = buildFilter(VISIT_PHASE, parseParams(visitPhase));

        String vesselVisitEtaFilter = buildSimpleTimeframeVesselParam(ETA, etaFrom, etaTo);
        String vesselVisitAtaFilter = buildSimpleTimeframeVesselParam(ATA, ataFrom, ataTo);
        String vesselVisitEtdFilter = buildSimpleTimeframeVesselParam(ETD, etdFrom, etdTo);
        String vesselVisitAtdFilter = buildSimpleTimeframeVesselParam(ATD, atdFrom, atdTo);

        personaFilters.add(personaFacilityId);
        personaFilters.add(personaCarrierName);
        personaFilters.add(personaCarrierOperatorId);
        personaFilters.add(personaCarrierVisitId);
        personaFilters.add(personaServiceId);
        personaFilters.add(personaVisitPhase);

        personaFilters.add(vesselVisitEtaFilter);
        personaFilters.add(vesselVisitAtaFilter);
        personaFilters.add(vesselVisitEtdFilter);
        personaFilters.add(vesselVisitAtdFilter);

        personaFilters = personaFilters.stream()
                .filter(e -> !e.equalsIgnoreCase(""))
                .filter(e -> !e.equalsIgnoreCase("1=1"))
                .collect(Collectors.toList());

        if (personaFilters.size() == 0) {
            queryBuilder.append(" AND ");
        }
        else {
            queryBuilder.append(String.format(" AND %s", "(" + String.join(" " + operationType + " ", personaFilters) + ")"));
            queryBuilder.append(" AND ");
        }

        // Search filter
        QueryBuilder filterBuilder = new QueryBuilder();

        if (query.filter != null) {
            String filter = filterBuilder.buildCosmosSearchFilter(query);
            queryBuilder.append(filter);
        }
        else queryBuilder.append("1=1");

        // Terminal condition
        if(!terminalConditions.contains("ALL")) {
            queryBuilder.append(" AND ");
            List<String> conditions = new ArrayList<>();
            for (String terminalCondition : terminalConditions) {
                conditions.add(String.format("c.facility_id = '%s'", terminalCondition));
            }
            queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
        }

        // Order
        if (!query.sort.isEmpty()) {
            String sortBy = filterBuilder.buildOrderByString(query.sort);
            queryBuilder.append(String.format(" ORDER BY %s", sortBy));
        }

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        return getVesselVisitDto(rawData);

    }


}
