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
            "TimestampToDateTime(c.ata) as ata,\n" +
            "TimestampToDateTime(c.atd) as atd,\n" +
            "c.driver_license_nbr,\n" +
            "c.truck_license_nbr,\n" +
            "TimestampToDateTime(c.entered_yard) as entered_yard,\n" +
            "TimestampToDateTime(c.exited_yard) as exited_yard,\n" +
            "TimestampToDateTime(c.placed_time) as placed_time,\n" +
            "c.to_location,\n" +
            "c.move_kind,\n" +
            "c.from_location,\n" +
            "c.category,\n" +
            "c.freight_kind,\n" +
            "c.placed_by,\n" +
            "c.event_type,\n" +
            "c.applied_to_id " +
            "FROM truck_visit c " +
            "WHERE ((c.from_location = 'TRUCK' AND IS_DEFINED(c.from_location)) OR (c.to_location = 'TRUCK' AND IS_DEFINED(c.to_location))) " +
            "AND c.delete_flag = 'N'";
    public static final String CONTAINER_NAME = "truck_visit";
    // dev container: truck_visit_api

    public static final String TRUCK_LICENSE_NBR = "(c.truck_license_nbr IN (%s) AND IS_DEFINED(c.truck_license_nbr))";
    public static final String MOVE_KIND = "(c.move_kind IN (%s) AND IS_DEFINED(c.move_kind))";

    public static final String VISIT_TIME = "(('%s' >= TimestampToDateTime(c.ata) AND '%s' <= TimestampToDateTime(c.atd)) AND IS_DEFINED(c.ata) AND IS_DEFINED(c.atd))";


}
