package com.justanalytics.constant;

public final class TruckVisitBaseCondition {

    public static final String DEFAULT_CONDITION = "1=1";

    public static final String TRUCK_VISIT_BASE_QUERY = "SELECT c.UniqueKey,\n" +
            "c.Facility_ID,\n" +
            "c.TruckID,\n" +
            "c.Visit_Nbr,\n" +
            "c.Visit_Phase,\n" +
            "c.Carrier_Operator_ID,\n" +
            "c.Carrier_Operator_Name,\n" +
            "TimestampToDateTime(c.ATA) as ATA,\n" +
            "TimestampToDateTime(c.ATD) as ATD,\n" +
            "c.Driver_License_Nbr,\n" +
            "c.Truck_License_Nbr,\n" +
            "TimestampToDateTime(c.Entered_Yard) as Entered_Yard,\n" +
            "TimestampToDateTime(c.Exited_Yard) as Exited_Yard,\n" +
            "TimestampToDateTime(c.PlacedTime) as PlacedTime,\n" +
            "c.ToLocation,\n" +
            "c.MoveKind,\n" +
            "c.FromLocation,\n" +
            "c.category,\n" +
            "c.freight_kind,\n" +
            "c.Placed_By,\n" +
            "c.Event_Type,\n" +
            "c.Applied_To_ID " +
            "FROM truck_visit_api c WHERE ((c.FromLocation = 'TRUCK' AND IS_DEFINED(c.FromLocation)) OR (c.ToLocation = 'TRUCK' AND IS_DEFINED(c.ToLocation)))";
    public static final String CONTAINER_NAME = "truck_visit_api";
    // dev container: truck_visit_api

    public static final String TRUCK_LICENSE_NBR = "(c.Truck_License_Nbr IN (%s) AND IS_DEFINED(c.Truck_License_Nbr))";
    public static final String MOVE_KIND = "(c.MoveKind IN (%s) AND IS_DEFINED(c.MoveKind))";

    public static final String VISIT_TIME = "(('%s' >= TimestampToDateTime(c.ATA) AND '%s' <= TimestampToDateTime(c.ATD)) AND IS_DEFINED(c.ATA) AND IS_DEFINED(c.ATD))";


}
