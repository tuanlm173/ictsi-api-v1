package com.justanalytics.service;

import com.justanalytics.config.CosmosDbProperties;
import com.justanalytics.dto.LanguageDescription;
import com.justanalytics.dto.TruckTransactionsDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.justanalytics.constant.TruckTransactionsBaseCondition.*;

@Service
public class TruckingTransactionsServiceImpl implements TruckingTransactionsService {

    Logger logger = LoggerFactory.getLogger(VesselVisitServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CosmosDbProperties cosmosDbProperties;

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

    private List<TruckTransactionsDto> getTruckTransactionsDto(List<JSONObject> rawData) {

        List<TruckTransactionsDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data : rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String operatorId = String.valueOf(data.get("operator_id"));
            String complexId = String.valueOf(data.get("complex_id"));
            String facilityId = String.valueOf(data.get("facility_id"));
            Long sequenceNumber = Objects.nonNull(data.get("sequence_number")) ? Long.parseLong(String.valueOf(data.get("sequence_number"))) : null;
            String transactionType = String.valueOf(data.get("transaction_type"));
            String containerNbr = String.valueOf(data.get("container_nbr"));
            String containerGkey = String.valueOf(data.get("container_gkey"));
            String ctrUfvGkey = String.valueOf(data.get("ctr_ufv_gkey"));
            String bookingNo = String.valueOf(data.get("booking_no"));
            String freightKind = String.valueOf(data.get("freight_kind"));
            String category = String.valueOf(data.get("category"));
            String lineOperator = String.valueOf(data.get("line_operator"));
            String origin = String.valueOf(data.get("origin"));
            String shipper = String.valueOf(data.get("shipper"));
            String appointmentStartDate = String.valueOf(data.get("appointment_start_date"));
            String appointmentEndDate = String.valueOf(data.get("appointment_end_date"));
            String truckPlate = String.valueOf(data.get("truck_plate"));
            String truckVisitGkey = String.valueOf(data.get("truck_visit_gkey"));
            String status = String.valueOf(data.get("status"));
            String truckVisitEnteredYard = String.valueOf(data.get("truck_visit_entered_yard"));
            String truckVisitExitedYard = String.valueOf(data.get("truck_visit_exited_yard"));
            String driverLicense = String.valueOf(data.get("driver_license"));
            String truckingCompany = String.valueOf(data.get("trucking_company"));
            String truckingCompanyGkey = String.valueOf(data.get("trucking_company_gkey"));
            String stageId = String.valueOf(data.get("stage_id"));
            String handled = String.valueOf(data.get("handled"));
            String loadDischargeTime = String.valueOf(data.get("load_discharge_time"));
            Boolean showTvarrivalStatus = Objects.nonNull(data.get("show_tvarrival_status")) ? Boolean.valueOf(String.valueOf(data.get("show_tvarrival_status"))) : null;
            String tvArrivalStatus = String.valueOf(data.get("tv_arrival_status"));

            List<LanguageDescription> tvArrivalRemarks = new ArrayList<>();
            List<LanguageDescription> rawTvArrivalRemarks = (List<LanguageDescription>) data.get("tv_arrival_remarks");
            if (rawTvArrivalRemarks != null) tvArrivalRemarks = rawTvArrivalRemarks;


            results.add(TruckTransactionsDto.builder()
                    .uniqueKey(uniqueKey)
                    .operatorId(operatorId)
                    .complexId(complexId)
                    .facilityId(facilityId)
                    .sequenceNumber(sequenceNumber)
                    .transactionType(transactionType)
                    .containerNbr(containerNbr)
                    .containerGkey(containerGkey)
                    .ctrUfvGkey(ctrUfvGkey)
                    .bookingNo(bookingNo)
                    .freightKind(freightKind)
                    .category(category)
                    .lineOperator(lineOperator)
                    .origin(origin)
                    .shipper(shipper)
                    .appointmentStartDate(appointmentStartDate)
                    .appointmentEndDate(appointmentEndDate)
                    .truckPlate(truckPlate)
                    .truckVisitGkey(truckVisitGkey)
                    .status(status)
                    .truckVisitEnteredYard(truckVisitEnteredYard)
                    .truckVisitExitedYard(truckVisitExitedYard)
                    .driverLicense(driverLicense)
                    .truckingCompany(truckingCompany)
                    .truckingCompanyGkey(truckingCompanyGkey)
                    .stageId(stageId)
                    .handled(handled)
                    .loadDischargeTime(loadDischargeTime)
                    .showTvarrivalStatus(showTvarrivalStatus)
                    .tvArrivalStatus(tvArrivalStatus)
                    .tvArrivalRemarks(tvArrivalRemarks)
                    .build());

        }

        return results;
    }



    @Override
    public List<TruckTransactionsDto> findTruckTransactions(
            Query query,
            String truckCompany,
            String truckPlate,
            String uniqueKey,
            LocalDateTime truckVisitTimeFrom,
            LocalDateTime truckVisitTimeTo,
            String operationType,
            List<String> terminalConditions) {

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(TRUCK_TRANSACTIONS_BASE_QUERY);

        // Persona filter
        List<String> personaFilters = new ArrayList<>();

        String personaTruckCompany = buildFilter(TRUCKING_COMPANY, parseParams(truckCompany));
        String personaTruckPlate = buildFilter(TRUCK_PLATE, parseParams(truckPlate));
        String personaUniqueKey = buildFilter(UNIQUE_KEY, parseParams(uniqueKey));
        String truckVisitTimeFilter = buildSimpleTimeframeVesselParam(TRUCK_VISIT_TIME, truckVisitTimeFrom, truckVisitTimeTo);

        personaFilters.add(personaTruckCompany);
        personaFilters.add(personaTruckPlate);
        personaFilters.add(personaUniqueKey);
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

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
//        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(cosmosDbProperties.getGetTruckTransactionsCnt(), sql);
        return getTruckTransactionsDto(rawData);
    }
}
