package com.justanalytics.constant;

public final class VesselVisitBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String VESSEL_VISIT_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.terminal_operator_id,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.carrier_visit_id,\n" +
            "c.carrier_name,\n" +
            "c.lloyds_id,\n" +
            "c.carrier_mode,\n" +
            "c.visit_nbr,\n" +
            "c.visit_phase,\n" +
            "c.carrier_operator_id,\n" +
            "c.carrier_operator_name,\n" +
            "c.eta,\n" +
            "c.ata,\n" +
            "c.etd,\n" +
            "c.atd,\n" +
            "c.begin_receive,\n" +
            "c.cargo_cutoff,\n" +
            "c.haz_cutoff,\n" +
            "c.reefer_cutoff,\n" +
            "c.labor_onboard,\n" +
            "c.labor_offboard,\n" +
            "c.arrival_off_port,\n" +
            "c.departure_off_port,\n" +
            "c.pilot_onboard,\n" +
            "c.pilot_offboard,\n" +
            "c.start_work,\n" +
            "c.end_work,\n" +
            "c.classification,\n" +
            "c.estimated_load_moves,\n" +
            "c.estimated_discharge_moves,\n" +
            "c.estimated_restow_moves,\n" +
            "c.estimated_shift_onboard_moves,\n" +
            "c.estimated_breakbulk_load_moves,\n" +
            "c.estimated_breakbulk_discharge_moves,\n" +
            "c.countrycode,\n" +
            "c.flex_string01,\n" +
            "c.flex_string02,\n" +
            "c.flex_string03,\n" +
            "c.ib_vyg,\n" +
            "c.ob_vyg,\n" +
            "c.quay_id,\n" +
            "c.quay_name,\n" +
            "c.service_id,\n" +
            "c.service_name,\n" +
            "c.remarks,\n" +
            "c.est_time_of_completion,\n" +
            "c.amended_est_time_of_completion,\n" +
            "c.estimated_time_of_berthing,\n" +
            "c.actual_time_of_berthing,\n" +
            "c.loading_cutoff,\n" +
            "c.export_cutoff,\n" +
            "c.vessel_registry_number,\n" +
            "c.vessel_statuses \n" +
            "FROM api_vessel_visit c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";
    public static final String CONTAINER_NAME = "api_vessel_visit";

    public static final String FACILITY_ID = "(c.facility_id IN (%s) AND IS_DEFINED(c.facility_id))";
    public static final String CARRIER_NAME = "(c.carrier_name IN (%s) AND IS_DEFINED(c.carrier_name))";
    public static final String CARRIER_OPERATOR_ID = "(c.carrier_operator_id IN (%s) AND IS_DEFINED(c.carrier_operator_id))";
    public static final String CARRIER_VISIT_ID = "(c.carrier_visit_id IN (%s) AND IS_DEFINED(c.carrier_visit_id))";
    public static final String SERVICE_ID = "(c.service_id IN (%s) AND IS_DEFINED(c.service_id))";
    public static final String VISIT_PHASE = "(c.visit_phase IN (%s) AND IS_DEFINED(c.visit_phase))";

    public static final String ETA = "(('%s' <= c.eta AND c.eta <= '%s') AND IS_DEFINED(c.eta))";
    public static final String ATA = "(('%s' <= c.ata AND c.ata <= '%s') AND IS_DEFINED(c.ata))";
    public static final String ETD = "(('%s' <= c.etd AND c.etd <= '%s') AND IS_DEFINED(c.etd))";
    public static final String ATD = "(('%s' <= c.atd AND c.atd <= '%s') AND IS_DEFINED(c.atd))";

}
