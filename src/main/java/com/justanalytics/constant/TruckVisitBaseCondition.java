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
            "c.truck_license_nbr,\n" +
            "c.entered_yard,\n" +
            "c.exited_yard,\n" +
            "c.placed_time,\n" +
            "c.to_location,\n" +
            "c.move_kind,\n" +
            "c.from_location,\n" +
            "c.category,\n" +
            "c.freight_kind,\n" +
            "c.placed_by,\n" +
            "c.event_type,\n" +
            "c.applied_to_id " +
            "FROM api_truck_visit c " +
            "WHERE ((c.from_location = 'TRUCK' AND IS_DEFINED(c.from_location)) OR (c.to_location = 'TRUCK' AND IS_DEFINED(c.to_location))) " +
            "AND c.delete_flag = 'N'";
    public static final String CONTAINER_NAME = "api_truck_visit";

    public static final String TRUCK_LICENSE_NBR = "(c.truck_license_nbr IN (%s) AND IS_DEFINED(c.truck_license_nbr))";
    public static final String MOVE_KIND = "(c.move_kind IN (%s) AND IS_DEFINED(c.move_kind))";

    public static final String VISIT_TIME = "(('%s' >= c.ata AND '%s' <= c.atd) AND IS_DEFINED(c.ata) AND IS_DEFINED(c.atd))";


}
