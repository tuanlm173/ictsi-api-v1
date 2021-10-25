package com.justanalytics.constant;

public final class VesselEventBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String VESSEL_EVENT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator_id,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.yard_id,\n" +
            "c.placed_by,\n" +
            "c.placed_time,\n" +
            "c.event_type,\n" +
            "c.event_descriptions,\n" +
            "c.notifiable,\n" +
            "c.vessel_gkey,\n" +
            "c.applied_to_id,\n" +
            "c.notes,\n" +
            "c.field_changes \n" +
            "FROM api_vessel_event c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String VESSEL_EVENT_CONTAINER_NAME = "api_vessel_event";

    public static final String UNIQUE_KEY = "(c.vessel_gkey IN (%s) AND IS_DEFINED(c.vessel_gkey))";
    public static final String LANGUAGE = "(ARRAY_CONTAINS(c.event_descriptions, {'language': %s}, true))";

}
