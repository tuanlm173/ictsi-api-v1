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
@JsonPropertyOrder({"unique_key", "terminal_operator_id", "complex_id", "facility_id", "carrier_visit_id", "carrier_name",
        "lloyds_id", "carrier_mode", "visit_nbr", "visit_phase", "carrier_operator_id", "carrier_operator_name", "eta", "ata",
        "etd", "atd", "begin_receive", "cargo_cutoff", "haz_cutoff", "reefer_cutoff", "labor_onboard", "labor_offboard",
        "arrival_off_port", "departure_off_port", "pilot_onboard", "pilot_offboard", "start_work", "end_work", "classification",
        "estimated_load_moves", "estimated_discharge_moves", "estimated_restow_moves", "estimated_shift_onboard_moves",
        "estimated_breakbulk_load_moves", "estimated_breakbulk_discharge_moves", "countrycode", "flex_string01", "flex_string02",
        "flex_string03", "ib_vyg", "ob_vyg", "quay_id", "quay_name", "service_id", "service_name", "est_time_of_completion",
        "amended_est_time_of_completion", "estimated_time_of_berthing", "actual_time_of_berthing", "loading_cutoff", "export_cutoff",
        "vessel_registry_number", "vessel_status"})
public class VesselVisitDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "terminal_operator_id")
    private String terminalOperatorId;
    @JsonProperty(value = "complex_id")
    private String complexId;
    @JsonProperty(value = "facility_id")
    private String facilityId;
    @JsonProperty(value = "carrier_visit_id")
    private String carrierVisitId;
    @JsonProperty(value = "carrier_name")
    private String carrierName;
    @JsonProperty(value = "lloyds_id")
    private String lloydsId;
    @JsonProperty(value = "carrier_mode")
    private String carrierMode;
    @JsonProperty(value = "visit_nbr")
    private String visitNbr;
    @JsonProperty(value = "visit_phase")
    private String visitPhase;
    @JsonProperty(value = "carrier_operator_id")
    private String carrierOperatorId;
    @JsonProperty(value = "carrier_operator_name")
    private String carrierOperatorName;
    @JsonProperty(value = "eta")
    private String eta;
    @JsonProperty(value = "ata")
    private String ata;
    @JsonProperty(value = "etd")
    private String etd;
    @JsonProperty(value = "atd")
    private String atd;
    @JsonProperty(value = "begin_receive")
    private String beginReceive;
    @JsonProperty(value = "cargo_cutoff")
    private String cargoCutoff;
    @JsonProperty(value = "haz_cutoff")
    private String hazCutoff;
    @JsonProperty(value = "reefer_cutoff")
    private String reeferCutoff;
    @JsonProperty(value = "labor_onboard")
    private String laborOnboard;
    @JsonProperty(value = "labor_offboard")
    private String laborOffboard;
    @JsonProperty(value = "arrival_off_port")
    private String arrivalOffPort;
    @JsonProperty(value = "departure_off_port")
    private String departureOffPort;
    @JsonProperty(value = "pilot_onboard")
    private String pilotOnboard;
    @JsonProperty(value = "pilot_offboard")
    private String pilotOffboard;
    @JsonProperty(value = "start_work")
    private String startWork;
    @JsonProperty(value = "end_work")
    private String endWork;
    @JsonProperty(value = "classification")
    private String classification;
    @JsonProperty(value = "estimated_load_moves")
    private Integer estimatedLoadMoves;
    @JsonProperty(value = "estimated_discharge_moves")
    private Integer estimatedDischargeMoves;
    @JsonProperty(value = "estimated_restow_moves")
    private Integer estimatedRestowMoves;
    @JsonProperty(value = "estimated_shift_onboard_moves")
    private Integer estimatedShiftOnboardMoves;
    @JsonProperty(value = "estimated_breakbulk_load_moves")
    private Integer estimatedBreakbulkLoadMoves;
    @JsonProperty(value = "estimated_breakbulk_discharge_moves")
    private Integer estimatedBreakbulkDischargeMoves;
    @JsonProperty(value = "countrycode")
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
    @JsonProperty(value = "quay_id")
    private String quayId;
    @JsonProperty(value = "quay_name")
    private String quayName;
    @JsonProperty(value = "service_id")
    private String serviceId;
    @JsonProperty(value = "service_name")
    private String serviceName;

    @JsonProperty(value = "est_time_of_completion")
    private String estTimeOfCompletion;
    @JsonProperty(value = "amended_est_time_of_completion")
    private String amendedEstTimeOfCompletion;
    @JsonProperty(value = "estimated_time_of_berthing")
    private String estimatedTimeOfBerthing;
    @JsonProperty(value = "actual_time_of_berthing")
    private String actualTimeOfBerthing;
    @JsonProperty(value = "loading_cutoff")
    private String loadingCutoff;
    @JsonProperty(value = "export_cutoff")
    private String exportCutoff;
    @JsonProperty(value = "vessel_registry_number")
    private String vesselRegistryNumber;
    @JsonProperty(value = "vessel_status")
    private String vesselStatus;
}
