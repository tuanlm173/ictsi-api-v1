package com.justanalytics.constant;

public final class CustomerBaseCondition {

    public static final String CUSTOMER_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.facility_id,\n" +
            "c.terminal_customer_id,\n" +
            "c.terminal_account_name,\n" +
            "c.terminal_customer_role,\n" +
            "c.account_number,\n" +
            "c.account_name,\n" +
            "c.account_type,\n" +
            "c.parent_account_name,\n" +
            "c.parent_account_number,\n" +
            "c.industry,\n" +
            "c.tax_id1,\n" +
            "c.tax_id2,\n" +
            "c.address,\n" +
            "c.update_ts \n" +
            "FROM api_customer c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String CUSTOMER_CONTAINER_NAME = "api_customer";

    public static final String CUSTOMER_TYPE = "(c.terminal_customer_role IN (%s) AND IS_DEFINED(c.terminal_customer_role))";
    public static final String FACILITY_ID = "(c.facility_id IN (%s) AND IS_DEFINED(c.facility_id))"; //"(ARRAY_CONTAINS(c.facility_ids, %s, true))";
    public static final String CUSTOMER_NAME = "((c.terminal_account_name LIKE %s%s%s AND IS_DEFINED(c.terminal_account_name)) OR (c.account_name LIKE %s%s%s AND IS_DEFINED(c.account_name)))";
    public static final String TAX_ID = "((c.tax_id1 IN (%s) AND IS_DEFINED(c.tax_id1)) OR (c.tax_id2 IN (%s) AND IS_DEFINED(c.tax_id2)))";
    public static final String UPDATE_TS = "(c.update_ts >= '%s')";

}
