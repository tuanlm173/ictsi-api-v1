package com.justanalytics.constant;

public final class TruckVisitBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String TRUCK_VISIT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.facility_id,\n" +
            "c.truck_id,\n" +
            "c.visit_nbr,\n" +
            "c.visit_phase,\n" +
            "c.carrier_operator_id,\n" +
            "c.carrier_operator_name,\n" +
            "c.ata,\n" +
            "c.atd,\n" +
            "c.driver_license_nbr,\n" +
            "c.truck_visit_gkey,\n" +
            "c.truck_license_nbr,\n" +
            "c.entered_yard,\n" +
            "c.exited_yard,\n" +
            "c.stage_id,\n" +
            "c.visit_statuses \n" +
            "FROM api_truck_visit_all c " +
            "WHERE (1=1) AND c.delete_flag = 'N' AND %s AND ((isnull(c.exited_yard) = false AND c.exited_yard >= %s) OR (isnull(c.exited_yard) = true)) AND c.facility_id NOT IN ('CGT')";
    public static final String CONTAINER_NAME = "api_truck_visit_all";

    public static final String FACILITY_ID = "(c.facility_id IN (%s) AND IS_DEFINED(c.facility_id))";
    public static final String TRUCK_LICENSE_NBR = "(c.truck_license_nbr IN (%s) AND IS_DEFINED(c.truck_license_nbr))";
    public static final String VISIT_PHASE = "(c.visit_phase IN (%s) AND IS_DEFINED(c.visit_phase))";
    public static final String CARRIER_OPERATOR_NAME = "(c.carrier_operator_name IN (%s) AND IS_DEFINED(c.carrier_operator_name))";

    public static final String VISIT_TIME = "(('%s' >= c.ata AND '%s' <= c.atd) AND IS_DEFINED(c.ata) AND IS_DEFINED(c.atd))";

    public static final String GLOBAL_TRUCK_VISIT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.facility_id,\n" +
            "c.truck_id,\n" +
            "c.visit_nbr,\n" +
            "c.visit_phase,\n" +
            "c.carrier_operator_id,\n" +
            "c.carrier_operator_name,\n" +
            "c.ata,\n" +
            "c.atd,\n" +
            "c.driver_license_nbr,\n" +
            "c.truck_visit_gkey,\n" +
            "c.truck_license_nbr,\n" +
            "c.entered_yard,\n" +
            "c.exited_yard,\n" +
            "c.stage_id,\n" +
            "c.visit_statuses \n" +
            "FROM api_truck_visit_all c " +
            "WHERE (1=1) AND c.delete_flag = 'N' AND %s " + // last visit flag
            "AND ((isnull(c.exited_yard) = false AND c.exited_yard >= %s) OR (isnull(c.exited_yard) = true)) " + // exited yard
            "AND c.facility_id NOT IN ('CGT') " +
            "AND c.facility_id IN (%s) " + // facility id
            "AND %s";
}
