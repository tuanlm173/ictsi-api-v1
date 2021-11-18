package com.justanalytics.service;

import com.justanalytics.dto.CustomerDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.justanalytics.constant.CustomerBaseCondition.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private DataRepository dataRepository;

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

    private List<CustomerDto> getCustomerDto(List<JSONObject> rawData) {

        List<CustomerDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String terminalCustomerId = String.valueOf(data.get("terminal_customer_id"));
            String terminalAccountName = String.valueOf(data.get("terminal_account_name"));
            String terminalCustomerRole = String.valueOf(data.get("terminal_customer_role"));
            String accountNumber = String.valueOf(data.get("account_number"));
            String accountName = String.valueOf(data.get("account_name"));
            String accountType = String.valueOf(data.get("account_type"));
            String parentAccountName = String.valueOf(data.get("parent_account_name"));
            String parentAccountNumber = String.valueOf(data.get("parent_account_number"));
            String industry = String.valueOf(data.get("industry"));

            results.add(CustomerDto.builder()
                    .uniqueKey(uniqueKey)
                    .terminalCustomerId(terminalCustomerId)
                    .terminalAccountName(terminalAccountName)
                    .terminalCustomerRole(terminalCustomerRole)
                    .accountNumber(accountNumber)
                    .accountName(accountName)
                    .accountType(accountType)
                    .parentAccountName(parentAccountName)
                    .parentAccountNumber(parentAccountNumber)
                    .industry(industry)
                    .build());
        }

        return results;


    }


    @Override
    public List<CustomerDto> findCustomer(
            Query query,
            String customerType,
            String operationType) {

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(CUSTOMER_BASE_QUERY);

        // Persona filter
        List<String> personaFilters = new ArrayList<>();
        String customerTypeFilter = buildFilter(CUSTOMER_TYPE, parseParams(customerType));
        personaFilters.add(customerTypeFilter);

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

        // Order
        if (!query.sort.isEmpty()) {
            String sortBy = filterBuilder.buildOrderByString(query.sort);
            queryBuilder.append(String.format(" ORDER BY %s", sortBy));
        }

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CUSTOMER_CONTAINER_NAME, sql);
        return getCustomerDto(rawData);
    }
}
