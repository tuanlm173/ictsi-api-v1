package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"UniqueKey", "Facility_ID", "TruckID", "Visit_Nbr", "Visit_Phase", "Carrier_Operator_ID",
        "Carrier_Operator_Name", "ATA", "ATD", "Driver_License_Nbr", "Truck_License_Nbr", "Entered_Yard",
        "Exited_Yard", "PlacedTime", "ToLocation", "MoveKind", "FromLocation", "category", "freight_kind", "Placed_By",
        "Event_Type", "Applied_To_ID"})
public class TruckVisitDto {

    @JsonProperty(value = "UniqueKey")
    private String uniqueKey;
    @JsonProperty(value = "Facility_ID")
    private String facilityId;
    @JsonProperty(value = "TruckID")
    private String truckId;
    @JsonProperty(value = "Visit_Nbr")
    private String visitNbr;
    @JsonProperty(value = "Visit_Phase")
    private String visitPhase;
    @JsonProperty(value = "Carrier_Operator_ID")
    private String carrierOperatorId;
    @JsonProperty(value = "Carrier_Operator_Name")
    private String carrierOperatorName;
    @JsonProperty(value = "ATA")
    private String ata;
    @JsonProperty(value = "ATD")
    private String atd;
    @JsonProperty(value = "Driver_License_Nbr")
    private String driverLicenseNbr;
    @JsonProperty(value = "Truck_License_Nbr")
    private String truckLicenseNbr;
    @JsonProperty(value = "Entered_Yard")
    private String enteredYard;
    @JsonProperty(value = "Exited_Yard")
    private String exitedYard;
    @JsonProperty(value = "PlacedTime")
    private String placedTime;
    @JsonProperty(value = "ToLocation")
    private String toLocation;
    @JsonProperty(value = "MoveKind")
    private String moveKind;
    @JsonProperty(value = "FromLocation")
    private String fromLocation;
    @JsonProperty(value = "category")
    private String category;
    @JsonProperty(value = "freight_kind")
    private String freightKind;
    @JsonProperty(value = "Placed_By")
    private String placedBy;
    @JsonProperty(value = "Event_Type")
    private String eventType;
    @JsonProperty(value = "Applied_To_ID")
    private String appliedToId;




}
