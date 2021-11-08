package com.justanalytics.constant;

public final class TruckTransactionsBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String TRUCK_TRANSACTIONS_BASE_QUERY = "SELECT " +
            "c.unique_key,\n" +
            "c.operator_id,\n" +
            "c.complex_id,\n" +
            "c.facility_id,\n" +
            "c.sequence_number,\n" +
            "c.transaction_type,\n" +
            "c.container_nbr,\n" +
            "c.container_gkey,\n" +
            "c.ctr_ufv_gkey,\n" +
            "c.booking_no,\n" +
            "c.freight_kind,\n" +
            "c.category,\n" +
            "c.line_operator,\n" +
            "c.origin,\n" +
            "c.shipper,\n" +
            "c.appointment_start_date,\n" +
            "c.appointment_end_date,\n" +
            "c.truck_plate,\n" +
            "c.truck_visit_gkey,\n" +
            "c.status,\n" +
            "c.truck_visit_entered_yard,\n" +
            "c.truck_visit_exited_yard,\n" +
            "c.driver_license,\n" +
            "c.trucking_company,\n" +
            "c.trucking_company_gkey,\n" +
            "c.stage_id,\n" +
            "c.handled,\n" +
            "c.load_discharge_time,\n" +
            "c.show_tvarrival_status,\n" +
            "c.tv_arrival_status,\n" +
            "c.tv_arrival_remarks \n" +
            "FROM api_truck_transactions c " +
            "WHERE (1=1) AND c.delete_flag = 'N'";

    public static final String CONTAINER_NAME = "api_truck_transactions";

    public static final String TRUCKING_COMPANY = "(c.trucking_company IN (%s) AND IS_DEFINED(c.trucking_company))";
    public static final String TRUCK_PLATE = "(c.truck_plate IN (%s) AND IS_DEFINED(c.truck_plate))";
    public static final String UNIQUE_KEY = "(c.truck_visit_gkey IN (%s) AND IS_DEFINED(c.truck_visit_gkey))";
    public static final String TRUCK_VISIT_TIME = "((c.truck_visit_entered_yard <= '%s' AND '%s' <= c.truck_visit_exited_yard) AND IS_DEFINED(c.truck_visit_entered_yard) AND IS_DEFINED(c.truck_visit_exited_yard))";

}
