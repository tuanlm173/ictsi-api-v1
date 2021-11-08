package com.justanalytics.utils;

import com.justanalytics.exception.InvalidParameterException;
import com.justanalytics.query.Query;
import com.justanalytics.query.Sort;
import com.justanalytics.query.filter.*;
import com.justanalytics.query.schema.DatabricksDataType;
import com.justanalytics.query.schema.DatasetSchema;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class QueryBuilder {

    private String buildBetweenFilter(BetweenFilter betweenFilter, Function<String, String> conversionFunction) {
        String fromString = conversionFunction.apply(betweenFilter.getFrom().toString());
        String toString = conversionFunction.apply(betweenFilter.getTo().toString());
        return String.format("%s BETWEEN %s AND %s", betweenFilter.getField(), fromString, toString);
    }

    private String buildComparisonFilter(ComparisonFilter comparisonFilter, Function<String, String> conversionFunction) {
        String valueString = conversionFunction.apply(comparisonFilter.getValue().toString());
        return String.format("%s %s %s", comparisonFilter.getField(), comparisonFilter.getCondition().value, valueString);
    }

    private String buildINFilter(INFilter inFilter, Function<String, String> conversionFunction) {
        String values = inFilter.getValues().stream()
                .map(value -> conversionFunction.apply(value.toString()))
                .collect(Collectors.joining(",", "(", ")"));
        return String.format("%s in %s", inFilter.getField(), values);
    }

    private String buildINCosmosFilter(INFilter inFilter) {
        String values = inFilter.getValues().stream()
                .map(value -> "'" + value.toString() + "'")
                .collect(Collectors.joining(", ", "(", ")"));
        Boolean negate = inFilter.getNegate();
        String negateCondition = "";
        if (negate) negateCondition = "NOT";
        return String.format("%s %s IN %s", "c." + inFilter.getField().toLowerCase(), negateCondition, values);
    }

    private String buildBetweenCosmosFilter(BetweenFilter betweenFilter) {
        String fromString = betweenFilter.getFrom().toString();
        String toString = betweenFilter.getTo().toString();
        List<String> betweenFields = Arrays.stream(betweenFilter.getField().split(","))
                .map(String::toLowerCase).map(field -> "c." + field).collect(Collectors.toList());
        if (betweenFields.size() != 2) {
            throw new InvalidParameterException("Between filter parameters must be two (2)");
        }
        return String.format("'%s' <= %s AND %s <= '%s'",
                fromString, betweenFields.get(0), betweenFields.get(1), toString);
    }

    //TODO: buildComparisonCosmosFilter
    private String buildComparisonCosmosFilter(ComparisonFilter comparisonFilter) {
        String comparedValue = comparisonFilter.getValue().toString();
        return String.format("%s %s %s", "c." + comparisonFilter.getField().toLowerCase(), comparisonFilter.getCondition().value, comparedValue);
    }

    private Function<String, String> getConversionFunction(String dataType) {
        switch(DatabricksDataType.valueOf(dataType.toUpperCase())) {
            case BYTE:
            case TINYINT:
            case SHORT:
            case SMALLINT:
            case INT:
            case INTEGER:
                // return (value) -> ConversionUtil.convertToInteger(value).toString();
            case BIGINT:
            case LONG:
                // return (value) -> ConversionUtil.convertToLong(value).toString();
            case BOOLEAN:
                // return (value) -> ConversionUtil.convertToBoolean(value).toString();
            case FLOAT:
            case REAL:
                // return (value) -> ConversionUtil.convertToFloat(value).toString();
            case DEC:
            case DECIMAL:
            case DOUBLE:
            case NUMERIC:
                // return (value) -> ConversionUtil.convertToDouble(value).toString();
                return value -> value.toString();
            case DATE:
                return (value) -> {
                    String  dateString = ConversionUtil.convertToDate(value).toString();
                    return String.format("'%s'", dateString);
                };
            case TIMESTAMP:
                return (value) -> {
                    String timestampString = ConversionUtil.convertToTimestamp(value).toString();
                    return String.format("'%s'", timestampString);
                };
            case INTERVAL:
            case BINARY:
            default:
                return (value) -> String.format("'%s'", value.toString());
        }
    }

    private String buildSimpleCosmosFilter(SimpleFilter simpleFilter) {
        switch(simpleFilter.getType()) {
            case IN:
                return buildINCosmosFilter((INFilter) simpleFilter);
            case BETWEEN:
                return buildBetweenCosmosFilter((BetweenFilter) simpleFilter);
            case COMPARISON:
                return buildComparisonCosmosFilter((ComparisonFilter) simpleFilter);
            default:
                return null;
        }
    }

    private String buildCompoundCosmosFilter(CompoundFilter compoundFilter) {
        String operator = String.format(" %s ", compoundFilter.getOperator().toString());
        return compoundFilter.getFilters().stream()
                .map(filter -> buildCosmosFilterQuery(filter))
                .collect(Collectors.joining(operator, "(", ")"));
    }

    private String buildCosmosFilterQuery(Filter filter) {
        String filterQueryString = "";
        switch(filter.getType()) {
            case COMPARISON:
            case BETWEEN:
            case IN:
                SimpleFilter simpleFilter = (SimpleFilter) filter;
                filterQueryString = buildSimpleCosmosFilter(simpleFilter);
                break;
            case COMPOUND:
                filterQueryString = buildCompoundCosmosFilter((CompoundFilter) filter);
                break;
            default:
                break;
        }
//        if(filter.getNegate()) {
//            return String.format("NOT %s", filterQueryString);
//        }
        return filterQueryString;
    }



    private String buildSimpleFilter(SimpleFilter simpleFilter, String dataType) {
        Function<String, String> conversionFunction = getConversionFunction(dataType);
        switch(simpleFilter.getType()) {
            case COMPARISON:
                return buildComparisonFilter((ComparisonFilter) simpleFilter, conversionFunction);
            case BETWEEN:
                return buildBetweenFilter((BetweenFilter) simpleFilter, conversionFunction);
            case IN:
                return buildINFilter((INFilter) simpleFilter, conversionFunction);
            default:
                return null;
        }
    }

    private String buildCompoundFilter(CompoundFilter compoundFilter, Map<String, String> schema) {
        String operator = String.format(" %s ", compoundFilter.getOperator().toString());
        return compoundFilter.getFilters().stream()
                .map(filter -> buildFilterQuery(filter, schema))
                .collect(Collectors.joining(operator, "(", ")"));
    }

    private String buildFilterQuery(Filter filter, Map<String, String> schema) {
        String filterQueryString = "";
        switch(filter.getType()) {
            case COMPARISON:
            case BETWEEN:
            case IN:
                SimpleFilter simpleFilter = (SimpleFilter) filter;
                String dataType = schema.get(simpleFilter.getField());
                filterQueryString = buildSimpleFilter(simpleFilter, dataType);
                break;
            case COMPOUND:
                filterQueryString = buildCompoundFilter((CompoundFilter) filter, schema);
                break;
            default:
                break;
        }
        if(filter.getNegate()) {
            return String.format("NOT %s", filterQueryString);
        }
        return filterQueryString;
    }

    private String prepareSelectedAttributes(List<String> selectedFields) {
        return selectedFields.stream().collect(Collectors.joining(", "));
    }

    public String buildOrderByString(List<Sort> orderBy) {
        return orderBy.stream().map(sort -> String.format("%s %s", "c." + sort.by.toLowerCase(), sort.order.value.toUpperCase()))
                .collect(Collectors.joining(", "));
    }

    //TODO: need to change to adapt cosmos db query
    public String build(Query query, DatasetSchema datasetSchema) {
        StringBuilder queryString = new StringBuilder("SELECT");
        // Selected Attributes
        queryString.append(String.format(" %s", prepareSelectedAttributes(query.select)));
        // FROM System.Dataset
        queryString.append(String.format(" FROM %s.%s", query.getDatabase(), query.getDataset()));
        //Filter
        if(query.filter != null) {
            String filterQuery = buildFilterQuery(query.filter, datasetSchema.getSchema());
            queryString.append(String.format(" WHERE %s", filterQuery));
        }
        //Sort
        if(!query.sort.isEmpty()) {
            String sortByString = buildOrderByString(query.sort);
            queryString.append(String.format(" ORDER BY %s", sortByString));
        }

        queryString.append(" LIMIT " + query.top);

        return queryString.toString();
    }

    public String buildCosmosSearchFilter(Query query) {
        StringBuilder filterString = new StringBuilder("");
        if (query.filter != null) {
            Filter filter = query.filter;
            String filterQuery = buildCosmosFilterQuery(filter);
            filterString.append(filterQuery);
        }
        return filterString.toString();

    }
}