package com.justanalytics.constant;

public final class EmptyContainerBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String EMPTY_CONTAINER_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator_id,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.visit_state,\n" +
            "c.container_nbr,\n" +
            "c.equipment_type,\n" +
            "c.teu,\n" +
            "c.line_operator_id,\n" +
            "c.line_operator_name,\n" +
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
            "c.time_in,\n" +
            "c.time_out,\n" +
            "c.booking_number,\t\t\n" +
            "c.requires_power,\n" +
            "c.time_state_change,\n" +
            "c.pod,\n" +
            "c.transit_state,\n" +
            "c.nominal_length,\n" +
            "c.reefer_type,\n" +
            "c.iso_group,\n" +
            "c.bill_of_lading_nbr as master_bl_nbr,\n" +
            "c.origin,\n" +
            "c.destination,\n" +
            "c.consignee_id,\n" +
            "c.consignee_name,\n" +
            "c.shipper_id,\n" +
            "c.shipper_name,\n" +
            "c.house_bl_nbr,\t\t\t\n" +
            "c.cargo_category,\n" +
            "c.cargo_consignee_id,\n" +
            "c.cargo_consignee_name,\n" +
            "c.cargo_shipper_id,\n" +
            "c.cargo_shipper_name,\n" +
            "c.cargo_origin,\n" +
            "c.shipper_declared_vgm,\n" +
            "c.terminal_measured_vgm,\n" +
            "c.last_free_day,\n" +
            "c.paid_thru_day,\n" +
            "c.power_last_free_day,\n" +
            "c.power_paid_thru_day,\n" +
            "c.ib_registry_nbr,\n" +
            "c.ob_registry_nbr,\n" +
            "c.entry_no,\n" +
            "c.requires_xray,\n" +
            "c.custom_tag,\n" +
            "c.ib_appointment_start_date,\n" +
            "c.ib_appointment_end_date,\n" +
            "c.ob_appointment_start_date,\n" +
            "c.ob_appointment_end_date,\n" +
            "c.shipper,\n" +
            "c.consignee,\n" +
            "c.show_tvarrival_status,\n" +
            "c.tv_arrival_status,\n" +
            "c.tv_arrival_remarks,\n" +
            "c.ib_tv_arrival_status,\n" +
            "c.ob_tv_arrival_status,\n" +
            "c.ib_tv_arrival_remarks,\n" +
            "c.ob_tv_arrival_remarks,\n" +
            "c.house_bls,\n" +
            "c.ib_id,\n" +
            "c.ib_cv_mode,\n" +
            "c.ib_carrier_name,\n" +
            "c.ib_operator_name,\n" +
            "c.ib_inbound_vyg,\n" +
            "c.ib_outbound_vyg,\n" +
            "c.ob_id,\n" +
            "c.ob_cv_mode,\n" +
            "c.ob_carrier_name,\n" +
            "c.ob_operator_name,\n" +
            "c.ob_inbound_vyg,\n" +
            "c.ob_outbound_vyg,\n" +
            "c.bizu_lineoper_gkey,\n" +
            "c.bizu_ibcarrier_gkey,\n" +
            "c.bizu_obcarrier_gkey,\n" +
            "c.bizu_shipper_gkey,\n" +
            "c.bizu_consignee_gkey,\n" +
            "c.remarks,\n" +
            "c.transit_state_descriptions " +
            "FROM c " +
            "WHERE ((c.category = 'STRGE' AND IS_DEFINED(c.category)) " +
            "AND ((c.freight_kind = 'MTY' " +
            "AND IS_DEFINED(c.freight_kind)))) " +
            "AND c.delete_flag = 'N' " +
            "AND %s " +
//            "AND ((isnull(c.time_out) = false AND c.time_out >= %s) OR (isnull(c.time_out) = true)) " +
            "AND c.facility_id NOT IN ('CGT') " +
            "AND c.freight_kind != 'BBK'";
    public static final String EMPTY_CONTAINER_NAME = "api_container_all";

    public static final String EMPTY_CONTAINER_FACILITY = "(c.facility_id IN (%s) AND IS_DEFINED(c.facility_id))";
    public static final String EMPTY_CONTAINER_VISIT_STATE = "(c.visit_state IN (%s) AND IS_DEFINED(c.visit_state))";
    public static final String EMPTY_CONTAINER_TRANSIT_STATE = "(c.transit_state IN (%s) AND IS_DEFINED(c.transit_state))";
    public static final String EMPTY_CONTAINER_ISO_GROUP = "(c.iso_group IN (%s) AND IS_DEFINED(c.iso_group))";
    public static final String EMPTY_CONTAINER_ARRIVE_POS_LOCTYPE = "(c.arrive_pos_loctype IN (%s) AND IS_DEFINED(c.arrive_pos_loctype))";
    public static final String EMPTY_CONTAINER_DEPART_POST_LOCTYPE = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (c.last_pos_loctype IN (%s) AND IS_DEFINED(c.last_pos_loctype)))";
    public static final String EMPTY_CONTAINER_DEPART_POST_LOC_ID = "((c.visit_state = '3DEPARTED' AND IS_DEFINED(c.visit_state)) AND (c.last_pos_locid IN (%s) AND IS_DEFINED(c.last_pos_locid)))";
    public static final String EMPTY_CONTAINER_ARRIVE_POS_LOC_ID = "(c.arrive_pos_locid IN (%s) AND IS_DEFINED(c.arrive_pos_locid))";
    public static final String EMPTY_CONTAINER_NUMBER = "(c.container_nbr IN (%s) AND IS_DEFINED(c.container_nbr))";
    public static final String EMPTY_CONTAINER_EQUIPMENT_TYPE = "(c.equipment_type IN (%s) AND IS_DEFINED(c.equipment_type))";
    public static final String EMPTY_CONTAINER_OPERATION_LINE_ID = "(c.line_operator_id IN (%s) AND IS_DEFINED(c.line_operator_id))";

    public static final String EMPTY_CONTAINER_TIME_IN = "((c.time_in >= '%s' AND c.time_in <= '%s') AND IS_DEFINED(c.time_in))";
    public static final String EMPTY_CONTAINER_TIME_OUT = "((c.time_out >= '%s' AND c.time_out <= '%s') AND IS_DEFINED(c.time_out))";

    public static final String EMPTY_CONTAINER_UNIQUE_KEY = "(c.unique_key IN (%s) AND IS_DEFINED(c.unique_key))";
    public static final String EMPTY_CONTAINER_BOL_NUMBER = "(c.bill_of_lading_nbr IN (%s) AND IS_DEFINED(c.bill_of_lading_nbr))";
    public static final String EMPTY_CONTAINER_BOOKING_NUMBER = "(c.booking_number IN (%s) AND IS_DEFINED(c.booking_number))";

}
