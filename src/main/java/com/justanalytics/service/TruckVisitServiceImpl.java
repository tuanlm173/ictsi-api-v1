package com.justanalytics.service;

import com.justanalytics.config.CosmosDbProperties;
import com.justanalytics.dto.LanguageDescription;
import com.justanalytics.utils.QueryBuilder;
import com.justanalytics.dto.TruckVisitDto;
import com.justanalytics.query.Query;
import com.justanalytics.repository.DataRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.justanalytics.constant.TruckVisitBaseCondition.*;

@Service
public class TruckVisitServiceImpl implements TruckVisitService {

    Logger logger = LoggerFactory.getLogger(TruckVisitServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String currentTime = "'" + LocalDateTime.now().minusDays(180).format(localDateTimeFormatter) + "Z'"; // DEV: 90 PROD: 45

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CosmosDbProperties cosmosDbProperties;

    private String filterLastVisitFlag(String lastVisitFlag) {
        String results = "1=1";
        if (lastVisitFlag != null && !lastVisitFlag.isBlank()) {
            if (lastVisitFlag.equalsIgnoreCase("true")) {
                results = "c.last_visit_flag = 1";
            }
        }
        return results;
    }

    private StringBuilder buildSimpleTruckVisitQuery(String query, String size) {
        StringBuilder queryBuilder = new StringBuilder();
        return queryBuilder.append(String.format(query, size));
    }

    private String buildSimpleTruckParam(String filter, String inputTruckVisitFields) {
        if (inputTruckVisitFields != null && !inputTruckVisitFields.isBlank()) {
            String[] truckVisits = inputTruckVisitFields.split(",");
            List<String> results = new ArrayList<>();
            for (String truckVisit: truckVisits) {
                results.add(String.format(filter, truckVisit));
            }
            return "(" + String.join(" OR ", results) + ")";
        }
        else return DEFAULT_CONDITION;
    }

    private String buildSimpleTimeframeTruckParam(String filter, LocalDateTime from, LocalDateTime to) {
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

    private List<TruckVisitDto> getTruckVisitDto(List<JSONObject> rawData) {

        List<TruckVisitDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String facilityId = String.valueOf(data.get("facility_id"));
            String truckId = String.valueOf(data.get("truck_id"));
            String visitNbr = String.valueOf(data.get("visit_nbr"));
            String visitPhase = String.valueOf(data.get("visit_phase"));
            String carrierOperatorId = String.valueOf(data.get("carrier_operator_id"));
            String carrierOperatorName = String.valueOf(data.get("carrier_operator_name"));
            String ata = String.valueOf(data.get("ata"));
            String atd = String.valueOf(data.get("atd"));
            String truckVisitGkey = String.valueOf(data.get("truck_visit_gkey"));
            String driverLicenseNbr = String.valueOf(data.get("driver_license_nbr"));
            String truckLicenseNbr = String.valueOf(data.get("truck_license_nbr"));
            String enteredYard = String.valueOf(data.get("entered_yard"));
            String exitedYard = String.valueOf(data.get("exited_yard"));
            String stageId = String.valueOf(data.get("stage_id"));

            List<LanguageDescription> visitStatus = new ArrayList<>();
            List<LanguageDescription> rawVisitStatus = (List<LanguageDescription>) data.get("visit_statuses");
            if (rawVisitStatus != null) visitStatus = rawVisitStatus;

            results.add(TruckVisitDto.builder()
                    .uniqueKey(uniqueKey)
                    .facilityId(facilityId)
                    .truckId(truckId)
                    .visitNbr(visitNbr)
                    .visitPhase(visitPhase)
                    .carrierOperatorId(carrierOperatorId)
                    .carrierOperatorName(carrierOperatorName)
                    .ata(ata)
                    .atd(atd)
                    .driverLicenseNbr(driverLicenseNbr)
                    .truckVisitGkey(truckVisitGkey)
                    .truckLicenseNbr(truckLicenseNbr)
                    .enteredYard(enteredYard)
                    .exitedYard(exitedYard)
                    .stageId(stageId)
                    .visitStatus(visitStatus)
                    .build());

        }
        return results;
    }

    @Override
    public List<TruckVisitDto> findTruckVisit(
            Query query,
            String facilityId,
            String truckLicenseNbrs,
            String visitPhases,
            String carrierOperatorNames,
            LocalDateTime visitTimeFrom,
            LocalDateTime visitTimeTo,
            String lastVisitFlag,
            String operationType,
            List<String> terminalConditions
    ) {

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(TRUCK_VISIT_BASE_QUERY, filterLastVisitFlag(lastVisitFlag)));

        // Persona filter
        List<String> personaFilters = new ArrayList<>();

        String personaFacilityIdFilter = buildFilter(FACILITY_ID, parseParams(facilityId));
        String personaTruckLicenseNbrFilter = buildFilter(TRUCK_LICENSE_NBR, parseParams(truckLicenseNbrs));
        String personaTruckVisitPhaseFilter = buildFilter(VISIT_PHASE, parseParams(visitPhases));
        String personaTruckCarrierOperatorNameFilter = buildFilter(CARRIER_OPERATOR_NAME, parseParams(carrierOperatorNames));
        String truckVisitTimeFilter = buildSimpleTimeframeTruckParam(VISIT_TIME, visitTimeFrom, visitTimeTo);

        personaFilters.add(personaFacilityIdFilter);
        personaFilters.add(personaTruckLicenseNbrFilter);
        personaFilters.add(personaTruckVisitPhaseFilter);
        personaFilters.add(personaTruckCarrierOperatorNameFilter);
        personaFilters.add(truckVisitTimeFilter);

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
        else {
            queryBuilder.append(" ORDER BY c.truck_license_nbr ASC, c.entered_yard DESC");
        }

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
//        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(cosmosDbProperties.getGetTruckVisitDetailsCnt(), sql);
        return getTruckVisitDto(rawData);

    }

    @Override
    public List<TruckVisitDto> findTruckVisitByTruckPlate(
            Query query,
            String visitPhases,
            String lastVisitFlag,
            String operationType
    ) {
        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(TRUCK_VISIT_BASE_QUERY, filterLastVisitFlag(lastVisitFlag), currentTime));

        // Persona filter
        List<String> personaFilters = new ArrayList<>();
        String personaTruckVisitPhaseFilter = buildFilter(VISIT_PHASE, parseParams(visitPhases));

        personaFilters.add(personaTruckVisitPhaseFilter);

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

        // Order - currently default sort
//        if (!query.sort.isEmpty()) {
//            String sortBy = filterBuilder.buildOrderByString(query.sort);
//            queryBuilder.append(String.format(" ORDER BY %s", sortBy));
//        }
//        else {
//            queryBuilder.append(" ORDER BY c.truck_license_nbr ASC, c.entered_yard DESC");
//        }
        queryBuilder.append(" ORDER BY c.truck_license_nbr ASC, c.entered_yard DESC");

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
//        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(cosmosDbProperties.getGetTruckVisitDetailsCnt(), sql);
        return getTruckVisitDto(rawData);
    }

    @Override
    public List<TruckVisitDto> findSimpleGlobalTruckVisit(
            Query query,
            String searchParam,
            String facilityId,
            String lastVisitFlag,
            String operationType
    ) {

        // Main query
        StringBuilder queryBuilder = new StringBuilder();

        // facility id
        String facilityIdFilter = "1=1";
        if (facilityId != null && !facilityId.isBlank())
            facilityIdFilter = buildFilter(FACILITY_ID, parseParams(facilityId));

        queryBuilder.append(String.format(GLOBAL_TRUCK_VISIT_BASE_QUERY,
                filterLastVisitFlag(lastVisitFlag), facilityIdFilter, buildFilter(TRUCK_LICENSE_NBR, parseParams(searchParam))));

        // Search filter
        QueryBuilder filterBuilder = new QueryBuilder();

        if (query.filter != null) {
            String filter = filterBuilder.buildCosmosSearchFilter(query);
            queryBuilder.append(filter);
        }
        else queryBuilder.append(" AND 1=1");

        queryBuilder.append(" ORDER BY c.truck_license_nbr ASC, c.entered_yard DESC");

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
//        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(cosmosDbProperties.getGetTruckVisitDetailsCnt(), sql);
        return getTruckVisitDto(rawData);
    }
}
