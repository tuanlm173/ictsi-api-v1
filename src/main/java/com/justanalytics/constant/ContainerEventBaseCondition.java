package com.justanalytics.constant;

public final class ContainerEventBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String CONTAINER_EVENT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator,\n" +
            "c.complex,\n" +
            "c.facility,\n" +
            "c.yard,\n" +
            "c.placed_by,\n" +
            "c.placed_time,\n" +
            "c.event_type,\n" +
            "c.event_descriptions,\n" +
            "c.notifiable,\n" +
            "c.container_gkey,\n" +
            "c.applied_to_id,\n" +
            "c.notes,\n" +
            "c.field_changes \n" +
            "FROM api_container_event c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String CONTAINER_EVENT_CONTAINER_NAME = "api_container_event";

    public static final String UNIQUE_KEY = "(c.container_gkey IN (%s) AND IS_DEFINED(c.container_gkey))";
    public static final String LANGUAGE = "(ARRAY_CONTAINS(c.event_descriptions, {'language': %s}, true))";

}
