package com.justanalytics.constant;

public final class FacilityBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String FACILITY_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.terminal_operator_id,\n" +
            "c.terminal_operator_name,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.facility_location,\n" +
            "c.facility_timezone,\n" +
            "c.terminal_portcd,\n" +
            "c.local_currency,\n" +
            "c.region\n" +
            "FROM c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String FACILITY_CONTAINER_NAME = "facility";

    public static final String FACILITY_ID = "(c.facility_id IN (%s) AND IS_DEFINED(c.facility_id))";
}
