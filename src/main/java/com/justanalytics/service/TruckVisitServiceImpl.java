package com.justanalytics.service;

import com.justanalytics.query.filter.DefaultFilter;
import com.justanalytics.query.filter.LogicalOperator;
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

    @Autowired
    private DataRepository dataRepository;

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

//    private String parseParams(List<String> params) {
//        if (params.size() == 0)
//            return "";
//        return String.join(", ", params);
//    }

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
            String driverLicenseNbr = String.valueOf(data.get("driver_license_nbr"));
            String truckLicenseNbr = String.valueOf(data.get("truck_license_nbr"));
            String enteredYard = String.valueOf(data.get("entered_yard"));
            String exitedYard = String.valueOf(data.get("exited_yard"));
            String placedTime = String.valueOf(data.get("placed_time"));
            String toLocation = String.valueOf(data.get("to_location"));
            String moveKind = String.valueOf(data.get("move_kind"));
            String fromLocation = String.valueOf(data.get("from_location"));
            String category = String.valueOf(data.get("category"));
            String freightKind = String.valueOf(data.get("freight_kind"));
            String placedBy = String.valueOf(data.get("placed_by"));
            String eventType = String.valueOf(data.get("event_type"));
            String appliedToId = String.valueOf(data.get("applied_to_id"));

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
                    .truckLicenseNbr(truckLicenseNbr)
                    .enteredYard(enteredYard)
                    .exitedYard(exitedYard)
                    .placedTime(placedTime)
                    .toLocation(toLocation)
                    .moveKind(moveKind)
                    .fromLocation(fromLocation)
                    .category(category)
                    .freightKind(freightKind)
                    .placedBy(placedBy)
                    .eventType(eventType)
                    .appliedToId(appliedToId)
                    .build());

        }
        return results;
    }

    @Override
    public List<TruckVisitDto> findTruckVisit(
            String truckLicenseNbr,
            String moveKind,
            LocalDateTime visitTimeFrom,
            LocalDateTime visitTimeTo,
            String size,
            List<String> terminalConditions
    ) {

        List<String> filters = new ArrayList<>();

        StringBuilder queryBuilder = buildSimpleTruckVisitQuery(TRUCK_VISIT_BASE_QUERY, size);

        String truckLicenseNbrFilter = buildSimpleTruckParam(TRUCK_LICENSE_NBR, truckLicenseNbr);
        String truckMoveKindFilter = buildSimpleTruckParam(MOVE_KIND, moveKind);
        String truckVisitTimeFilter = buildSimpleTimeframeTruckParam(VISIT_TIME, visitTimeFrom, visitTimeTo);

        filters.add(truckLicenseNbrFilter);
        filters.add(truckMoveKindFilter);
        filters.add(truckVisitTimeFilter);

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
            queryBuilder.append(" ORDER BY c.PlacedTime DESC");
        }
        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        return getTruckVisitDto(rawData);
    }

    @Override
    public List<TruckVisitDto> findTruckVisitV2(
            String truckLicenseNbrs,
            String moveKinds,
            LocalDateTime visitTimeFrom,
            LocalDateTime visitTimeTo,
            String filterTruckLicenseNbrs,
            String filterMoveKinds,
            String operationType,
            String size,
            List<String> terminalConditions
    ) {

        List<String> mandatoryFilters = new ArrayList<>();
        List<String> optionalFilters = new ArrayList<>();

        StringBuilder queryBuilder = buildSimpleTruckVisitQuery(TRUCK_VISIT_BASE_QUERY, size);

        String mandatoryTruckLicenseNbrFilter = buildFilter(TRUCK_LICENSE_NBR, parseParams(filterTruckLicenseNbrs));
        String mandatoryTruckMoveKindFilter = buildFilter(MOVE_KIND, parseParams(filterMoveKinds));

        mandatoryFilters.add(mandatoryTruckLicenseNbrFilter);
        mandatoryFilters.add(mandatoryTruckMoveKindFilter);

        mandatoryFilters = mandatoryFilters.stream().filter(e -> !Objects.equals(e, "") && !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

        String optionalTruckLicenseNbrFilter = buildFilter(TRUCK_LICENSE_NBR, parseParams(truckLicenseNbrs));
        String optionalTruckMoveKindFilter = buildFilter(MOVE_KIND, parseParams(moveKinds));
        String truckVisitTimeFilter = buildSimpleTimeframeTruckParam(VISIT_TIME, visitTimeFrom, visitTimeTo);

        optionalFilters.add(optionalTruckLicenseNbrFilter);
        optionalFilters.add(optionalTruckMoveKindFilter);
        optionalFilters.add(truckVisitTimeFilter);

        optionalFilters = optionalFilters.stream().filter(e -> !Objects.equals(e, "") && !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

        if (mandatoryFilters.size() == 0) {
            queryBuilder.append("");
        }
        else {
            queryBuilder.append(String.format(" AND %s", "(" + String.join(" " + operationType + " ", mandatoryFilters) + ")"));
        }

        if (optionalFilters.size() == 0) {
            queryBuilder.append("");
        }
        else {
            queryBuilder.append(String.format(" AND %s", "(" + String.join(" OR ", optionalFilters) + ")"));
        }

        if(!terminalConditions.contains("ALL")) {
            queryBuilder.append(" AND ");
            List<String> conditions = new ArrayList<>();
            for (String terminalCondition : terminalConditions) {
                conditions.add(String.format("c.Facility_ID = '%s'", terminalCondition));
            }
            queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
        }

        queryBuilder.append(" ORDER BY c.PlacedTime DESC");

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        return getTruckVisitDto(rawData);

    }

    @Override
    public List<TruckVisitDto> findTruckVisitV3(
            Query query,
            String truckLicenseNbrs,
            String moveKinds,
            String size,
            String operationType,
            List<String> terminalConditions
    ) {

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(TRUCK_VISIT_BASE_QUERY);
//        queryBuilder.append(" AND ");

        // Persona filter
        List<String> personaFilters = new ArrayList<>();

        String personaTruckLicenseNbrFilter = buildFilter(TRUCK_LICENSE_NBR, parseParams(truckLicenseNbrs));
        String personaTruckMoveKindFilter = buildFilter(MOVE_KIND, parseParams(moveKinds));

        personaFilters.add(personaTruckLicenseNbrFilter);
        personaFilters.add(personaTruckMoveKindFilter);

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
        String filter = filterBuilder.buildCosmosSearchFilter(query);
        queryBuilder.append(filter);

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
        return getTruckVisitDto(rawData);

    }
}
