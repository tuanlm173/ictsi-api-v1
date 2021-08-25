package com.justanalytics.constant;

public final class ImportContainerBaseCondition {

    public static final String IMPORT_CONTAINER_BASE_QUERY = "SELECT c.UniqueKey,\n" +
            "c.OperatorID,\n" +
            "c.ComplexID,\n" +
            "c.FacilityID,\n" +
            "c.visit_state,\n" +
            "c.ContainerNbr,\n" +
            "c.EquipmentType,\n" +
            "c.TEU,\n" +
            "c.OperatorLineID,\n" +
            "c.OperatorName,\n" +
            "c.create_time,\n" +
            "c.category,\n" +
            "c.freight_kind,\n" +
            "c.goods_and_ctr_wt_kg,\n" +
            "c.goods_ctr_wt_kg_advised,\n" +
            "c.goods_ctr_wt_kg_gate_measured,\n" +
            "c.goods_ctr_wt_kg_yard_measured,\n" +
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
            "TimestampToDateTime(c.time_in) as time_in,\n" +
            "TimestampToDateTime(c.time_out) as time_out,\n" +
            "c.BookingNumber,\t\t\n" +
            "c.requires_power,\n" +
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
            "TimestampToDateTime(c.time_state_change) as time_state_change,\n" +
            "c.POD,\n" +
            "c.transit_state,\n" +
            "c.nominal_length,\n" +
            "c.reefer_type,\n" +
            "c.iso_group,\n" +
            "c.Master_BLNbr,\n" +
            "c.Origin,\n" +
            "c.Destination,\n" +
            "c.Consignee_ID,\n" +
            "c.Consignee_Name,\n" +
            "c.Shipper_ID,\n" +
            "c.Shipper_Name,\n" +
            "c.House_BLNbr,\t\t\t\n" +
            "c.Cargo_Category,\n" +
            "c.Cargo_Consignee_ID,\n" +
            "c.Cargo_Consignee_Name,\n" +
            "c.Cargo_Shipper_ID,\n" +
            "c.Cargo_Shipper_Name,\n" +
            "c.Cargo_Origin " +
            "FROM container_import c " +
            "WHERE (c.category = 'IMPRT' AND IS_DEFINED(c.category)) AND IS_DEFINED(c.TEU)";
    public static final String IMPORT_CONTAINER_NAME = "container_import";

    public static final String IMPORT_DEFAULT_IMPED = "(c.visit_state = '1ACTIVE' AND IS_DEFINED(c.visit_state))";
    public static final String IMPORT_IMPED_TYPE_NONE = "((c.visit_state = '1ACTIVE' AND IS_DEFINED(c.visit_state)) AND (c.stopped_road = false AND IS_DEFINED(c.stopped_road)) AND (c.stopped_rail = false AND IS_DEFINED(c.stopped_rail)) AND IS_DEFINED(c.imped_road) = false AND IS_DEFINED(c.imped_rail) = false)";
    public static final String IMPORT_IMPED_TYPE_ANY = "((c.visit_state = '1ACTIVE' AND IS_DEFINED(c.visit_state)) AND ((c.stopped_road = true AND IS_DEFINED(c.stopped_road)) OR (c.stopped_rail = true AND IS_DEFINED(c.stopped_rail)) OR (c.stopped_vessel = true AND IS_DEFINED(c.stopped_vessel)) OR IS_DEFINED(c.imped_vessel) = true OR IS_DEFINED(c.imped_road) = true OR IS_DEFINED(c.imped_rail) = true))";
    public static final String IMPORT_IMPED_TYPE_TRUCK = "((c.visit_state = '1ACTIVE' AND IS_DEFINED(c.visit_state)) AND ((c.stopped_road = true AND IS_DEFINED(c.stopped_road)) OR IS_DEFINED(c.imped_road) = true))";
    public static final String IMPORT_IMPED_TYPE_VESSEL = "((c.visit_state = '1ACTIVE' AND IS_DEFINED(c.visit_state)) AND ((c.stopped_vessel = true AND IS_DEFINED(c.stopped_vessel)) OR IS_DEFINED(c.imped_vessel) = true))";
    public static final String IMPORT_IMPED_TYPE_RAIL = "((c.visit_state = '1ACTIVE' AND IS_DEFINED(c.visit_state)) AND ((c.stopped_rail = true AND IS_DEFINED(c.stopped_rail)) OR IS_DEFINED(c.imped_rail) = true))";

    public static final String IMPORT_CONTAINER_FREIGHT_KIND = "(c.freight_kind IN (%s) AND IS_DEFINED(c.freight_kind))";
    public static final String IMPORT_CONTAINER_VISIT_STATE = "(c.visit_state IN (%s) AND IS_DEFINED(c.visit_state))";
    public static final String IMPORT_CONTAINER_TRANSIT_STATE = "(c.transit_state IN (%s) AND IS_DEFINED(c.transit_state))";
    public static final String IMPORT_CONTAINER_ISO_GROUP = "(c.iso_group IN (%s) AND IS_DEFINED(c.iso_group))";
    public static final String IMPORT_CONTAINER_ARRIVE_POS_LOCTYPE = "(c.arrive_pos_loctype IN (%s) AND IS_DEFINED(c.arrive_pos_loctype))";
    public static final String IMPORT_CONTAINER_DEPART_POST_LOCTYPE = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (c.last_pos_loctype IN (%s) AND IS_DEFINED(c.last_pos_loctype)))";
    public static final String IMPORT_CONTAINER_DEPART_POST_LOC_ID = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (c.last_pos_locid IN (%s) AND IS_DEFINED(c.last_pos_locid)))";
    public static final String IMPORT_CONTAINER_ARRIVE_POS_LOC_ID = "(c.arrive_Pos_Locid IN (%s) AND IS_DEFINED(c.arrive_Pos_Locid))";
    public static final String IMPORT_CONTAINER_NUMBER = "(c.ContainerNbr IN (%s) AND IS_DEFINED(c.ContainerNbr))";
    public static final String IMPORT_CONTAINER_EQUIPMENT_TYPE = "(c.EquipmentType IN (%s) AND IS_DEFINED(c.EquipmentType))";
    public static final String IMPORT_CONTAINER_OPERATION_LINE_ID = "(c.OperatorLineID IN (%s) AND IS_DEFINED(c.OperatorLineID))";

    public static final String IMPORT_CONTAINER_TIME_IN = "((TimestampToDateTime(c.time_in) >= '%s' AND TimestampToDateTime(c.time_in) <= '%s') AND IS_DEFINED(c.time_in))";
    public static final String IMPORT_CONTAINER_TIME_OUT = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (TimestampToDateTime(c.time_out) >= '%s' AND TimestampToDateTime(c.time_out) <= '%s') AND IS_DEFINED(c.time_out))";

    public static final String IMPORT_CONTAINER_BOOKING_NUMBER = "(c.BookingNumber IN (%s) AND IS_DEFINED(c.BookingNumber))";
    public static final String IMPORT_CONTAINER_BOL_NUMBER = "((c.BillofLadingNbr = '%s' AND IS_DEFINED(c.BillofLadingNbr)) OR (c.House_BLNbr = '%s' AND IS_DEFINED(c.House_BLNbr)))";



}
