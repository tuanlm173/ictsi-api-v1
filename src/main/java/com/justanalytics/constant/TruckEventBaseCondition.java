package com.justanalytics.constant;

public final class TruckEventBaseCondition {

    public static final String TRUCK_EVENT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator_id,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.yard_id,\n" +
            "c.placed_by,\n" +
            "c.placed_time,\n" +
            "c.event_type,\n" +
            "c.event_descriptions,\n" +
            "c.container_gkey,\n" +
            "c.applied_to_id,\n" +
            "c.truck_visit_gkey,\n" +
            "c.notes,\n" +
            "c.field_changes,\n" +
            "c.category,\n" +
            "c.sub_category \n" +
            "FROM api_truck_event c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String TRUCK_EVENT_CONTAINER_NAME = "api_truck_event";

    public static final String UNIQUE_KEY = "(c.truck_visit_gkey IN (%s) AND IS_DEFINED(c.truck_visit_gkey))";
    public static final String LANGUAGE = "(ARRAY_CONTAINS(c.event_descriptions, {'language': %s}, true))";


}
