package com.justanalytics.constant;

public final class ContainerEventBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String CONTAINER_EVENT_BASE_QUERY = "SELECT " +
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
            "c.container_gkey,\n" +
            "c.applied_to_id,\n" +
            "c.notes,\n" +
            "c.field_changes,\n" +
            "c.category,\n" +
            "c.sub_category, \n" +
            "c.sequence \n" +
            "FROM c " +
            "WHERE (1=1) AND c.delete_flag = 'N' AND c.facility_id NOT IN ('CGT')";

    public static final String CONTAINER_EVENT_CONTAINER_NAME = "api_container_event_all";

    public static final String UNIQUE_KEY = "(c.container_gkey IN (%s) AND IS_DEFINED(c.container_gkey))";
    public static final String LANGUAGE = "(ARRAY_CONTAINS(c.event_descriptions, {'language': %s}, true))";

}
