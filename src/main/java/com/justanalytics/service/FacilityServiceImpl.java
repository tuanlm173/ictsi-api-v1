package com.justanalytics.service;

import com.justanalytics.dto.FacilityDto;
import com.justanalytics.query.Query;
import com.justanalytics.repository.DataRepository;
import com.justanalytics.utils.QueryBuilder;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.justanalytics.constant.FacilityBaseCondition.FACILITY_BASE_QUERY;
import static com.justanalytics.constant.FacilityBaseCondition.FACILITY_CONTAINER_NAME;

@Service
public class FacilityServiceImpl implements FacilityService {

    Logger logger = LoggerFactory.getLogger(TruckVisitServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private DataRepository dataRepository;

    private List<FacilityDto> getFacilityDto(List<JSONObject> rawData) {

        List<FacilityDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data : rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String terminalOperatorId = String.valueOf(data.get("terminal_operator_id"));
            String terminalOperatorName = String.valueOf(data.get("terminal_operator_name"));
            String complexId = String.valueOf(data.get("complex_id"));
            String facilityId = String.valueOf(data.get("facility_id"));
            String facilityLocation = String.valueOf(data.get("facility_location"));
            String facilityTimezone = String.valueOf(data.get("facility_timezone"));
            String terminalPortcd = String.valueOf(data.get("terminal_portcd"));
            String localCurrency = String.valueOf(data.get("local_currency"));
            String region = String.valueOf(data.get("region"));

            results.add(FacilityDto.builder()
                    .uniqueKey(uniqueKey)
                    .terminalOperatorId(terminalOperatorId)
                    .terminalOperatorName(terminalOperatorName)
                    .complexId(complexId)
                    .facilityId(facilityId)
                    .facilityLocation(facilityLocation)
                    .facilityTimezone(facilityTimezone)
                    .terminalPortcd(terminalPortcd)
                    .localCurrency(localCurrency)
                    .region(region)
                    .build()
            );
        }

        return results;
    }

    @Override
    public List<FacilityDto> findFacility(
            Query query) {

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(FACILITY_BASE_QUERY);

        queryBuilder.append(" AND ");

        // Search filter
        QueryBuilder filterBuilder = new QueryBuilder();

        if (query.filter != null) {
            String filter = filterBuilder.buildCosmosSearchFilter(query);
            queryBuilder.append(filter);
        }
        else queryBuilder.append("1=1");

        // Order
        if (!query.sort.isEmpty()) {
            String sortBy = filterBuilder.buildOrderByString(query.sort);
            queryBuilder.append(String.format(" ORDER BY %s", sortBy));
        }

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(FACILITY_CONTAINER_NAME, sql);
        return getFacilityDto(rawData);
    }
}
