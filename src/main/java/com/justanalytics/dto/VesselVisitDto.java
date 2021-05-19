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
@JsonPropertyOrder({"UniqueKey", "Terminal_Operator_ID", "Complex_ID", "Facility_ID", "Carrier_Visit_ID", "Carrier_Name",
        "Lloyds_ID", "Carrier_Mode", "Visit_Nbr", "Visit_Phase", "Carrier_Operator_ID", "Carrier_Operator_Name", "ETA", "ATA",
        "ETD", "ATD", "Begin_Receive", "Cargo_Cutoff", "Haz_Cutoff", "Reefer_Cutoff", "Labor_Onboard", "Labor_Offboard",
        "Arrival_Off_Port", "Departure_Off_Port", "Pilot_Onboard", "Pilot_Offboard", "Start_work", "End_Work", "Classification",
        "Estimated_Load_Moves", "Estimated_Discharge_Moves", "Estimated_Restow_Moves", "Estimated_Shift_Onboard_Moves",
        "Estimated_Breakbulk_Load_Moves", "Estimated_Breakbulk_Discharge_Moves", "CountryCode", "flex_string01", "flex_string02",
        "flex_string03", "ib_vyg", "ob_vyg", "Quay_ID", "Quay_Name", "service_ID", "service_Name"})
public class VesselVisitDto {

    @JsonProperty(value = "UniqueKey")
    private String uniqueKey;
    @JsonProperty(value = "Terminal_Operator_ID")
    private String terminalOperatorId;
    @JsonProperty(value = "Complex_ID")
    private String complexId;
    @JsonProperty(value = "Facility_ID")
    private String facilityId;
    @JsonProperty(value = "Carrier_Visit_ID")
    private String carrierVisitId;
    @JsonProperty(value = "Carrier_Name")
    private String carrierName;
    @JsonProperty(value = "Lloyds_ID")
    private String lloydsId;
    @JsonProperty(value = "Carrier_Mode")
    private String carrierMode;
    @JsonProperty(value = "Visit_Nbr")
    private String visitNbr;
    @JsonProperty(value = "Visit_Phase")
    private String visitPhase;
    @JsonProperty(value = "Carrier_Operator_ID")
    private String carrierOperatorId;
    @JsonProperty(value = "Carrier_Operator_Name")
    private String carrierOperatorName;
    @JsonProperty(value = "ETA")
    private String eta;
    @JsonProperty(value = "ATA")
    private String ata;
    @JsonProperty(value = "ETD")
    private String etd;
    @JsonProperty(value = "ATD")
    private String atd;
    @JsonProperty(value = "Begin_Receive")
    private String beginReceive;
    @JsonProperty(value = "Cargo_Cutoff")
    private String cargoCutoff;
    @JsonProperty(value = "Haz_Cutoff")
    private String hazCutoff;
    @JsonProperty(value = "Reefer_Cutoff")
    private String reeferCutoff;
    @JsonProperty(value = "Labor_Onboard")
    private String laborOnboard;
    @JsonProperty(value = "Labor_Offboard")
    private String laborOffboard;
    @JsonProperty(value = "Arrival_Off_Port")
    private String arrivalOffPort;
    @JsonProperty(value = "Departure_Off_Port")
    private String departureOffPort;
    @JsonProperty(value = "Pilot_Onboard")
    private String pilotOnboard;
    @JsonProperty(value = "Pilot_Offboard")
    private String pilotOffboard;
    @JsonProperty(value = "Start_work")
    private String startWork;
    @JsonProperty(value = "End_Work")
    private String endWork;
    @JsonProperty(value = "Classification")
    private String classification;
    @JsonProperty(value = "Estimated_Load_Moves")
    private Integer estimatedLoadMoves;
    @JsonProperty(value = "Estimated_Discharge_Moves")
    private Integer estimatedDischargeMoves;
    @JsonProperty(value = "Estimated_Restow_Moves")
    private Integer estimatedRestowMoves;
    @JsonProperty(value = "Estimated_Shift_Onboard_Moves")
    private Integer estimatedShiftOnboardMoves;
    @JsonProperty(value = "Estimated_Breakbulk_Load_Moves")
    private Integer estimatedBreakbulkLoadMoves;
    @JsonProperty(value = "Estimated_Breakbulk_Discharge_Moves")
    private Integer estimatedBreakbulkDischargeMoves;
    @JsonProperty(value = "CountryCode")
    private String countryCode;
    @JsonProperty(value = "flex_string01")
    private String flexString01;
    @JsonProperty(value = "flex_string02")
    private String flexString02;
    @JsonProperty(value = "flex_string03")
    private String flexString03;
    @JsonProperty(value = "ib_vyg")
    private String ibVyg;
    @JsonProperty(value = "ob_vyg")
    private String obVyg;
    @JsonProperty(value = "Quay_ID")
    private String quayId;
    @JsonProperty(value = "Quay_Name")
    private String quayName;
    @JsonProperty(value = "service_ID")
    private String serviceId;
    @JsonProperty(value = "service_Name")
    private String serviceName;

}
