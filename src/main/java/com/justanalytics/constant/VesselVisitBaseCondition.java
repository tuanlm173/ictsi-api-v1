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
            "TicksToDateTime(c.eta) as eta,\n" +
            "TicksToDateTime(c.ata) as ata,\n" +
            "TicksToDateTime(c.etd) as etd,\n" +
            "TicksToDateTime(c.atd) as atd,\n" +
            "TicksToDateTime(c.begin_receive) as begin_receive,\n" +
            "TicksToDateTime(c.cargo_cutoff) as cargo_cutoff,\n" +
            "TicksToDateTime(c.haz_cutoff) as haz_cutoff,\n" +
            "TicksToDateTime(c.reefer_cutoff) as reefer_cutoff,\n" +
            "TicksToDateTime(c.labor_onboard) as labor_onboard,\n" +
            "TicksToDateTime(c.labor_offboard) as labor_offboard,\n" +
            "TicksToDateTime(c.arrival_off_port) as arrival_off_port,\n" +
            "TicksToDateTime(c.departure_off_port) as departure_off_port,\n" +
            "TicksToDateTime(c.pilot_onboard) as pilot_onboard,\n" +
            "TicksToDateTime(c.pilot_offboard) as pilot_offboard,\n" +
            "TicksToDateTime(c.start_work) as start_work,\n" +
            "TicksToDateTime(c.end_work) as end_work,\n" +
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
            "TicksToDateTime(c.est_time_of_completion) as est_time_of_completion,\n" +
            "TicksToDateTime(c.amended_est_time_of_completion) as amended_est_time_of_completion,\n" +
            "TicksToDateTime(c.estimated_time_of_berthing) as estimated_time_of_berthing,\n" +
            "TicksToDateTime(c.actual_time_of_berthing) as actual_time_of_berthing,\n" +
            "TicksToDateTime(c.loading_cutoff) as loading_cutoff,\n" +
            "TicksToDateTime(c.export_cutoff) as export_cutoff,\n" +
            "c.vessel_registry_number,\n" +
            "c.vessel_status\n" +
            "FROM vessel_visit c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";
    public static final String CONTAINER_NAME = "vessel_visit";

    public static final String CARRIER_NAME = "(c.carrier_name IN (%s) AND IS_DEFINED(c.carrier_name))";
    public static final String CARRIER_OPERATOR_ID = "(c.carrier_operator_id IN (%s) AND IS_DEFINED(c.carrier_operator_id))";
    public static final String CARRIER_VISIT_ID = "(c.carrier_visit_id IN (%s) AND IS_DEFINED(c.carrier_visit_id))";
    public static final String SERVICE_ID = "(c.service_id IN (%s) AND IS_DEFINED(c.service_id))";
    public static final String VISIT_PHASE = "(c.visit_phase IN (%s) AND IS_DEFINED(c.visit_phase))";

    public static final String ETA = "(('%s' <= TicksToDateTime(c.eta) AND TicksToDateTime(c.ETA) <= '%s') AND IS_DEFINED(c.eta))";
    public static final String ATA = "(('%s' <= TicksToDateTime(c.ata) AND TicksToDateTime(c.ATA) <= '%s') AND IS_DEFINED(c.ata))";
    public static final String ETD = "(('%s' <= TicksToDateTime(c.etd) AND TicksToDateTime(c.ETD) <= '%s') AND IS_DEFINED(c.etd))";
    public static final String ATD = "(('%s' <= TicksToDateTime(c.atd) AND TicksToDateTime(c.ATD) <= '%s') AND IS_DEFINED(c.atd))";

}
