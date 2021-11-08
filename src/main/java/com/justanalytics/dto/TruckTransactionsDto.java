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
@JsonPropertyOrder({"unique_key", "operator_id", "complex_id", "facility_id", "sequence_number", "transaction_type",
        "container_nbr", "container_gkey", "ctr_ufv_gkey", "booking_no", "freight_kind", "category", "line_operator", "origin",
        "shipper", "appointment_start_date", "appointment_end_date", "truck_plate", "truck_visit_gkey", "status", "truck_visit_entered_yard", "truck_visit_exited_yard",
        "driver_license", "trucking_company", "trucking_company_gkey", "stage_id", "handled", "load_discharge_time", "show_tvarrival_status",
        "tv_arrival_status", "tv_arrival_remarks"})
public class TruckTransactionsDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "operator_id")
    private String operatorId;
    @JsonProperty(value = "complex_id")
    private String complexId;
    @JsonProperty(value = "facility_id")
    private String facilityId;
    @JsonProperty(value = "sequence_number")
    private Long sequenceNumber;
    @JsonProperty(value = "transaction_type")
    private String transactionType;
    @JsonProperty(value = "container_nbr")
    private String containerNbr;
    @JsonProperty(value = "container_gkey")
    private String containerGkey;
    @JsonProperty(value = "ctr_ufv_gkey")
    private String ctrUfvGkey;
    @JsonProperty(value = "booking_no")
    private String bookingNo;
    @JsonProperty(value = "freight_kind")
    private String freightKind;
    @JsonProperty(value = "category")
    private String category;
    @JsonProperty(value = "line_operator")
    private String lineOperator;
    @JsonProperty(value = "origin")
    private String origin;
    @JsonProperty(value = "shipper")
    private String shipper;
    @JsonProperty(value = "appointment_start_date")
    private String appointmentStartDate;
    @JsonProperty(value = "appointment_end_date")
    private String appointmentEndDate;
    @JsonProperty(value = "truck_plate")
    private String truckPlate;
    @JsonProperty(value = "truck_visit_gkey")
    private String truckVisitGkey;
    @JsonProperty(value = "status")
    private String status;
    @JsonProperty(value = "truck_visit_entered_yard")
    private String truckVisitEnteredYard;
    @JsonProperty(value = "truck_visit_exited_yard")
    private String truckVisitExitedYard;
    @JsonProperty(value = "driver_license")
    private String driverLicense;
    @JsonProperty(value = "trucking_company")
    private String truckingCompany;
    @JsonProperty(value = "trucking_company_gkey")
    private String truckingCompanyGkey;
    @JsonProperty(value = "stage_id")
    private String stageId;
    @JsonProperty(value = "handled")
    private String handled;
    @JsonProperty(value = "load_discharge_time")
    private String loadDischargeTime;
    @JsonProperty(value = "show_tvarrival_status")
    private String showTvarrivalStatus;
    @JsonProperty(value = "tv_arrival_status")
    private String tvArrivalStatus;
    @JsonProperty(value = "tv_arrival_remarks")
    private String tvArrivalRemarks;

}
