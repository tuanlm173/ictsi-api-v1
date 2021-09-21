package com.justanalytics.constant;

public final class ContainerBaseCondition {

    public static final String ALL_CONTAINER_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator_id,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.visit_state,\n" +
            "c.container_nbr,\n" +
            "c.equipment_type,\n" +
            "c.teu,\n" +
            "c.operator_line_id,\n" +
            "c.operator_name,\n" +
            "c.create_time,\n" +
            "c.category,\n" +
            "c.freight_kind,\n" +
            "c.seal_nbr1,\n" +
            "c.seal_nbr2,\n" +
            "c.seal_nbr3,\n" +
            "c.seal_nbr4,\n" +
            "c.stopped_vessel,\t\t\n" +
            "c.stopped_rail,\n" +
            "c.stopped_road,\n" +
            "c.imped_vessel,\t\t\n" +
            "c.imped_rail,\n" +
            "c.imped_road,\n" +
            "c.arrive_pos_loctype,\n" +
            "c.arrive_pos_locid,\n" +
            "c.arrive_pos_slot,\n" +
            "c.last_pos_loctype,\n" +
            "c.last_pos_locid,\n" +
            "c.last_pos_slot,\n" +
            "TicksToDateTime(c.time_in) as time_in,\n" +
            "TicksToDateTime(c.time_out) as time_out,\t\t\n" +
            "c.flex_string01,\n" +
            "c.flex_string02,\n" +
            "c.flex_string03,\n" +
            "c.flex_string04,\n" +
            "c.flex_string05,\n" +
            "c.flex_string06,\n" +
            "c.flex_string07,\n" +
            "c.flex_string08,\n" +
            "c.flex_string09,\n" +
            "c.flex_string10,\n" +
            "c.flex_string11,\n" +
            "c.flex_string12,\n" +
            "c.flex_string13,\n" +
            "c.flex_string14,\n" +
            "c.flex_string15,\n" +
            "TicksToDateTime(c.time_state_change) as time_state_change,\n" +
            "c.transit_state,\n" +
            "c.nominal_length,\n" +
            "c.reefer_type,\n" +
            "c.iso_group " +
            "FROM container c " +
            "WHERE (1=1) AND IS_DEFINED(c.teu) and c.delete_flag = 'N'";
    public static final String ALL_CONTAINER_NAME = "container";

    public static final String ALL_CONTAINER_VISIT_STATE = "(c.visit_state IN (%s) AND IS_DEFINED(c.visit_state))";
    public static final String ALL_CONTAINER_TRANSIT_STATE = "(c.transit_state IN (%s) AND IS_DEFINED(c.transit_state))";
    public static final String ALL_CONTAINER_ISO_GROUP = "(c.iso_group IN (%s) AND IS_DEFINED(c.iso_group))";
    public static final String ALL_CONTAINER_ARRIVE_POS_LOCTYPE = "(c.arrive_pos_loctype IN (%s) AND IS_DEFINED(c.arrive_pos_loctype))";
    public static final String ALL_CONTAINER_DEPART_POST_LOCTYPE = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (c.last_pos_loctype IN (%s) AND IS_DEFINED(c.last_pos_loctype)))";
    public static final String ALL_CONTAINER_DEPART_POST_LOC_ID = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (c.last_pos_locid IN (%s) AND IS_DEFINED(c.last_pos_locid)))";
    public static final String ALL_CONTAINER_ARRIVE_POS_LOC_ID = "(c.arrive_pos_locid IN (%s) AND IS_DEFINED(c.arrive_pos_locid))";
    public static final String ALL_CONTAINER_NUMBER = "(c.container_nbr IN (%s) AND IS_DEFINED(c.container_nbr))";
    public static final String ALL_CONTAINER_EQUIPMENT_TYPE = "(c.equipment_type IN (%s) AND IS_DEFINED(c.equipment_type))";
    public static final String ALL_CONTAINER_OPERATION_LINE_ID = "(c.operator_line_id IN (%s) AND IS_DEFINED(c.operator_line_id))";

    public static final String ALL_CONTAINER_TIME_IN = "((TicksToDateTime(c.time_in) >= '%s' AND TicksToDateTime(c.time_in) <= '%s') AND IS_DEFINED(c.time_in))";
    public static final String ALL_CONTAINER_TIME_OUT = "((TicksToDateTime(c.time_out) >= '%s' AND TicksToDateTime(c.time_out) <= '%s') AND IS_DEFINED(c.time_out))";
}
