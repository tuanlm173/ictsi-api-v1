package com.justanalytics.constant;

public final class VesselEventBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String VESSEL_EVENT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator,\n" +
            "c.complex,\n" +
            "c.facility,\n" +
            "c.yard,\n" +
            "c.placed_by,\n" +
            "TicksToDateTime(c.placed_time) as placed_time,\n" +
            "c.event_type,\n" +
            "c.event_description,\n" +
            "c.notifiable,\n" +
            "c.vessel_gkey,\n" +
            "c.applied_to_id,\n" +
            "c.notes,\n" +
            "c.field_changes,\n" +
            "c.language \n" +
            "FROM vessel_event c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String VESSEL_EVENT_CONTAINER_NAME = "vessel_event";

    public static final String UNIQUE_KEY = "(c.unique_key IN (%s) AND IS_DEFINED(c.unique_key))";
    public static final String LANGUAGE = "(c.language IN (%s) AND IS_DEFINED(c.language))";

}
