package com.justanalytics.constant;

public final class VesselVisitBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String VESSEL_VISIT_BASE_QUERY = "SELECT TOP %s c.UniqueKey,\n" +
            "c.Terminal_Operator_ID,\n" +
            "c.Complex_ID,\n" +
            "c.Facility_ID,\n" +
            "c.Carrier_Visit_ID,\n" +
            "c.Carrier_Name,\n" +
            "c.Lloyds_ID,\n" +
            "c.Carrier_Mode,\n" +
            "c.Visit_Nbr,\n" +
            "c.Visit_Phase,\n" +
            "c.Carrier_Operator_ID,\n" +
            "c.Carrier_Operator_Name,\n" +
            "TimestampToDateTime(c.ETA) as ETA,\n" +
            "TimestampToDateTime(c.ATA) as ATA,\n" +
            "TimestampToDateTime(c.ETD) as ETD,\n" +
            "TimestampToDateTime(c.ATD) as ATD,\n" +
            "TimestampToDateTime(c.Begin_Receive) as Begin_Receive,\n" +
            "TimestampToDateTime(c.Cargo_Cutoff) as Cargo_Cutoff,\n" +
            "TimestampToDateTime(c.Haz_Cutoff) as Haz_Cutoff,\n" +
            "TimestampToDateTime(c.Reefer_Cutoff) as Reefer_Cutoff,\n" +
            "TimestampToDateTime(c.Labor_Onboard) as Labor_Onboard,\n" +
            "TimestampToDateTime(c.Labor_Offboard) as Labor_Offboard,\n" +
            "TimestampToDateTime(c.Arrival_Off_Port) as Arrival_Off_Port,\n" +
            "TimestampToDateTime(c.Departure_Off_Port) as Departure_Off_Port,\n" +
            "TimestampToDateTime(c.Pilot_Onboard) as Pilot_Onboard,\n" +
            "TimestampToDateTime(c.Pilot_Offboard) as Pilot_Offboard,\n" +
            "TimestampToDateTime(c.Start_work) as Start_work,\n" +
            "TimestampToDateTime(c.End_Work) as End_Work,\n" +
            "c.Classification,\n" +
            "c.Estimated_Load_Moves,\n" +
            "c.Estimated_Discharge_Moves,\n" +
            "c.Estimated_Restow_Moves,\n" +
            "c.Estimated_Shift_Onboard_Moves,\n" +
            "c.Estimated_Breakbulk_Load_Moves,\n" +
            "c.Estimated_Breakbulk_Discharge_Moves,\n" +
            "c.CountryCode,\n" +
            "c.flex_string01,\n" +
            "c.flex_string02,\n" +
            "c.flex_string03,\n" +
            "c.ib_vyg,\n" +
            "c.ob_vyg,\n" +
            "c.Quay_ID,\n" +
            "c.Quay_Name,\n" +
            "c.service_ID,\n" +
            "c.service_Name " +
            "FROM vessel_visit c WHERE (1=1)";
    public static final String CONTAINER_NAME = "vessel_visit";

    public static final String CARRIER_OPERATOR_ID = "(c.Carrier_Operator_ID = '%s' AND IS_DEFINED(c.Carrier_Operator_ID))";
    public static final String CARRIER_VISIT_ID = "(c.Carrier_Visit_ID = '%s' AND IS_DEFINED(c.Carrier_Visit_ID))";
    public static final String SERVICE_ID = "(c.service_ID = '%s' AND IS_DEFINED(c.service_ID))";
    public static final String VISIT_PHASE = "(c.Visit_Phase = '%s' AND IS_DEFINED(c.Visit_Phase))";

    public static final String ETA = "(('%s' <= TimestampToDateTime(c.ETA) AND TimestampToDateTime(c.ETA) <= '%s') AND IS_DEFINED(c.ETA))";
    public static final String ATA = "(('%s' <= TimestampToDateTime(c.ATA) AND TimestampToDateTime(c.ATA) <= '%s') AND IS_DEFINED(c.ATA))";
    public static final String ETD = "(('%s' <= TimestampToDateTime(c.ETD) AND TimestampToDateTime(c.ETD) <= '%s') AND IS_DEFINED(c.ETD))";
    public static final String ATD = "(('%s' <= TimestampToDateTime(c.ATD) AND TimestampToDateTime(c.ATD) <= '%s') AND IS_DEFINED(c.ATD))";

}
