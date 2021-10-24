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
@JsonPropertyOrder({"unique_key", "operator_id", "complex_id", "facility_id", "visit_state", "container_nbr", "equipment_type",
        "teu", "operator_line_id", "operator_name", "create_time", "category", "freight_kind", "goods_and_ctr_wt_kg",
        "goods_ctr_wt_kg_advised", "goods_ctr_wt_kg_gate_measured", "goods_ctr_wt_kg_yard_measured", "seal_nbr1", "seal_nbr2",
        "seal_nbr3", "seal_nbr4", "stopped_vessel", "stopped_rail", "stopped_road", "imped_vessel", "imped_rail", "imped_road",
        "arrive_pos_loctype", "arrive_pos_locid", "arrive_pos_slot", "last_pos_loctype", "last_pos_locid", "last_pos_slot",
        "time_in", "time_out", "booking_number", "requires_power", "flex_string01", "flex_string02", "flex_string03", "flex_string04",
        "flex_string05", "flex_string06", "flex_string07", "flex_string08", "flex_string09", "flex_string10", "flex_string11",
        "flex_string12", "flex_string13", "flex_string14", "flex_string15", "time_state_change", "pod", "transit_state",
        "nominal_length", "reefer_type", "iso_group"})
public class ExportContainerDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "operator_id")
    private String operatorId;
    @JsonProperty(value = "complex_id")
    private String complexId;
    @JsonProperty(value = "facility_id")
    private String facilityId;
    @JsonProperty(value = "visit_state")
    private String visitState;
    @JsonProperty(value = "container_nbr")
    private String containerNbr;
    @JsonProperty(value = "equipment_type")
    private String equipmentType;
    @JsonProperty(value = "teu")
    private Float teu;
    @JsonProperty(value = "operator_line_id")
    private String operatorLineId;
    @JsonProperty(value = "operator_name")
    private String operatorName;
    @JsonProperty(value = "create_time")
    private String createTime;
    @JsonProperty(value = "category")
    private String category;
    @JsonProperty(value = "freight_kind")
    private String freightKind;
    @JsonProperty(value = "goods_and_ctr_wt_kg")
    private Float goodsAndCtrWtKg;
    @JsonProperty(value = "goods_ctr_wt_kg_advised")
    private Float goodsCtrWtKgAdvised;
    @JsonProperty(value = "goods_ctr_wt_kg_gate_measured")
    private Float goodsCtrWtKgGateMeasured;
    @JsonProperty(value = "goods_ctr_wt_kg_yard_measured")
    private Float goodsCtrWtKgYardMeasured;
    @JsonProperty(value = "seal_nbr1")
    private String sealNbr1;
    @JsonProperty(value = "seal_nbr2")
    private String sealNbr2;
    @JsonProperty(value = "seal_nbr3")
    private String sealNbr3;
    @JsonProperty(value = "seal_nbr4")
    private String sealNbr4;
    @JsonProperty(value = "stopped_vessel")
    private String stoppedVessel;
    @JsonProperty(value = "stopped_rail")
    private String stoppedRail;
    @JsonProperty(value = "stopped_road")
    private String stoppedRoad;
    @JsonProperty(value = "imped_vessel")
    private String impedVessel;
    @JsonProperty(value = "imped_rail")
    private String impedRail;
    @JsonProperty(value = "imped_road")
    private String impedRoad;
    @JsonProperty(value = "arrive_pos_loctype")
    private String arrivePosLoctype;
    @JsonProperty(value = "arrive_pos_locid")
    private String arrivePosLocId;
    @JsonProperty(value = "arrive_pos_slot")
    private String arrivePosSlot;
    @JsonProperty(value = "last_pos_loctype")
    private String lastPosLoctype;
    @JsonProperty(value = "last_pos_locid")
    private String lastPosLocId;
    @JsonProperty(value = "last_pos_slot")
    private String lastPosSlot;
    @JsonProperty(value = "time_in")
    private String timeIn;
    @JsonProperty(value = "time_out")
    private String timeOut;
    @JsonProperty(value = "booking_number")
    private String bookingNumber;
    @JsonProperty(value = "requires_power")
    private String requiresPower;
    @JsonProperty(value = "flex_string01")
    private String flexString01;
    @JsonProperty(value = "flex_string02")
    private String flexString02;
    @JsonProperty(value = "flex_string03")
    private String flexString03;
    @JsonProperty(value = "flex_string04")
    private String flexString04;
    @JsonProperty(value = "flex_string05")
    private String flexString05;
    @JsonProperty(value = "flex_string06")
    private String flexString06;
    @JsonProperty(value = "flex_string07")
    private String flexString07;
    @JsonProperty(value = "flex_string08")
    private String flexString08;
    @JsonProperty(value = "flex_string09")
    private String flexString09;
    @JsonProperty(value = "flex_string10")
    private String flexString10;
    @JsonProperty(value = "flex_string11")
    private String flexString11;
    @JsonProperty(value = "flex_string12")
    private String flexString12;
    @JsonProperty(value = "flex_string13")
    private String flexString13;
    @JsonProperty(value = "flex_string14")
    private String flexString14;
    @JsonProperty(value = "flex_string15")
    private String flexString15;
    @JsonProperty(value = "time_state_change")
    private String timeStateChange;
    @JsonProperty(value = "pod")
    private String pod;
    @JsonProperty(value = "transit_state")
    private String transitState;
    @JsonProperty(value = "nominal_length")
    private String nominalLength;
    @JsonProperty(value = "reefer_type")
    private String reeferType;
    @JsonProperty(value = "iso_group")
    private String isoGroup;

}
