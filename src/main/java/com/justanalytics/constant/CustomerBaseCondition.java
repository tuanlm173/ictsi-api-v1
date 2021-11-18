package com.justanalytics.constant;

public final class CustomerBaseCondition {

    public static final String CUSTOMER_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.terminal_customer_id,\n" +
            "c.terminal_account_name,\n" +
            "c.terminal_customer_role,\n" +
            "c.account_number,\n" +
            "c.account_name,\n" +
            "c.account_type,\n" +
            "c.parent_account_name,\n" +
            "c.parent_account_number,\n" +
            "c.industry \n" +
            "FROM api_customer c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String CUSTOMER_CONTAINER_NAME = "api_customer";

    public static final String CUSTOMER_TYPE = "(c.terminal_customer_role IN (%s) AND IS_DEFINED(c.terminal_customer_role))";
    public static final String FACILITY_ID = "(ARRAY_CONTAINS(c.facility_ids, %s, true))";

}
