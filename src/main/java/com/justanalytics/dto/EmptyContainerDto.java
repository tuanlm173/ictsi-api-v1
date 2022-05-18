package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"unique_key", "operator_id", "complex_id", "facility_id", "visit_state", "container_nbr", "equipment_type",
        "teu", "line_operator_id", "line_operator_name", "create_time", "category", "freight_kind", "goods_and_ctr_wt_kg",
        "goods_ctr_wt_kg_advised", "goods_ctr_wt_kg_gate_measured", "goods_ctr_wt_kg_yard_measured", "seal_nbr1", "seal_nbr2",
        "seal_nbr3", "seal_nbr4", "stopped_vessel", "stopped_rail", "stopped_road", "imped_vessel", "imped_rail", "imped_road",
        "arrive_pos_loctype", "arrive_pos_locid", "arrive_pos_slot", "last_pos_loctype", "last_pos_locid", "last_pos_slot",
        "time_in", "time_out", "booking_number", "requires_power", "time_state_change", "pod", "transit_state",
        "nominal_length", "reefer_type", "iso_group", "master_bl_nbr", "origin", "destination", "consignee_id", "consignee_name",
        "shipper_id", "shipper_name", "house_bl_nbr", "cargo_category", "cargo_consignee_id", "cargo_consignee_name",
        "cargo_shipper_id", "cargo_shipper_name", "cargo_origin", "shipper_declared_vgm", "terminal_measured_vgm", "last_free_day",
        "paid_thru_day", "power_last_free_day", "power_paid_thru_day", "ib_registry_nbr", "ob_registry_nbr", "entry_no",
        "requires_xray", "custom_tag", "ib_appointment_start_date", "ib_appointment_end_date", "ob_appointment_start_date", "ob_appointment_end_date",
        "shipper", "consignee", "show_tvarrival_status", "tv_arrival_status", "tv_arrival_remarks", "ib_tv_arrival_status", "ob_tv_arrival_status", "ib_tv_arrival_remarks", "ob_tv_arrival_remarks", "house_bls",
        "ib_id", "ib_cv_mode", "ib_carrier_name", "ib_operator_name", "ib_inbound_vyg", "ib_outbound_vyg", "ob_id", "ob_cv_mode", "ob_carrier_name",
        "ob_operator_name", "ob_inbound_vyg", "ob_outbound_vyg", "remarks", "transit_state_descriptions"})
public class EmptyContainerDto {

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
    @JsonProperty(value = "line_operator_id")
    private String operatorLineId;
    @JsonProperty(value = "line_operator_name")
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
    @JsonProperty(value = "master_bl_nbr")
    private String masterBlNbr;
    @JsonProperty(value = "origin")
    private String origin;
    @JsonProperty(value = "destination")
    private String destination;
    @JsonProperty(value = "consignee_id")
    private String consigneeId;
    @JsonProperty(value = "consignee_name")
    private String consigneeName;
    @JsonProperty(value = "shipper_id")
    private String shipperId;
    @JsonProperty(value = "shipper_name")
    private String shipperName;
    @JsonProperty(value = "house_bl_nbr")
    private String houseBlNbr;
    @JsonProperty(value = "cargo_category")
    private String cargoCategory;
    @JsonProperty(value = "cargo_consignee_id")
    private String cargoConsigneeId;
    @JsonProperty(value = "cargo_consignee_name")
    private String cargoConsigneeName;
    @JsonProperty(value = "cargo_shipper_id")
    private String cargoShipperId;
    @JsonProperty(value = "cargo_shipper_name")
    private String cargoShipperName;
    @JsonProperty(value = "cargo_origin")
    private String cargoOrigin;

    @JsonProperty(value = "shipper_declared_vgm")
    private String shipperDeclaredVgm;
    @JsonProperty(value = "terminal_measured_vgm")
    private String terminalMeasuredVgm;
    @JsonProperty(value = "last_free_day")
    private String lastFreeDay;
    @JsonProperty(value = "paid_thru_day")
    private String paidThruDay;
    @JsonProperty(value = "power_last_free_day")
    private String powerLastFreeDay;
    @JsonProperty(value = "power_paid_thru_day")
    private String powerPaidThruDay;
    @JsonProperty(value = "ib_registry_nbr")
    private String ibRegistryNbr;
    @JsonProperty(value = "ob_registry_nbr")
    private String obRegistryNbr;
    @JsonProperty(value = "entry_no")
    private String entryNo;
    @JsonProperty(value = "requires_xray")
    private String requiresXray;
    @JsonProperty(value = "custom_tag")
    private String customTag;
    @JsonProperty(value = "ib_appointment_start_date")
    private String ibAppointmentStartDate;
    @JsonProperty(value = "ib_appointment_end_date")
    private String ibAppointmentEndDate;
    @JsonProperty(value = "ob_appointment_start_date")
    private String obAppointmentStartDate;
    @JsonProperty(value = "ob_appointment_end_date")
    private String obAppointmentEndDate;
    @JsonProperty(value = "shipper")
    private String shipper;
    @JsonProperty(value = "consignee")
    private String consignee;
    @JsonProperty(value = "show_tvarrival_status")
    private String showTvarrivalStatus;
    @JsonProperty(value = "tv_arrival_status")
    private String tvArrivalStatus;
    @JsonProperty(value = "tv_arrival_remarks")
    private List<LanguageDescription> tvArrivalRemarks;
    @JsonProperty(value = "ib_tv_arrival_status")
    private String ibTvArrivalStatus;
    @JsonProperty(value = "ob_tv_arrival_status")
    private String obTvArrivalStatus;
    @JsonProperty(value = "ib_tv_arrival_remarks")
    private List<LanguageDescription> ibTvArrivalRemarks;
    @JsonProperty(value = "ob_tv_arrival_remarks")
    private List<LanguageDescription> obTvArrivalRemarks;
    @JsonProperty(value = "house_bls")
    private List<HouseBillOfLadings> houseBls;

    @JsonProperty(value = "ib_id")
    private String ibId;
    @JsonProperty(value = "ib_cv_mode")
    private String ibCvMode;
    @JsonProperty(value = "ib_carrier_name")
    private String ibCarrierName;
    @JsonProperty(value = "ib_operator_name")
    private String ibOperatorName;
    @JsonProperty(value = "ib_inbound_vyg")
    private String ibInboundVyg;
    @JsonProperty(value = "ib_outbound_vyg")
    private String ibOutboundVyg;
    @JsonProperty(value = "ob_id")
    private String obId;
    @JsonProperty(value = "ob_cv_mode")
    private String obCvMode;
    @JsonProperty(value = "ob_carrier_name")
    private String obCarrierName;
    @JsonProperty(value = "ob_operator_name")
    private String obOperatorName;
    @JsonProperty(value = "ob_inbound_vyg")
    private String obInboundVyg;
    @JsonProperty(value = "ob_outbound_vyg")
    private String obOutboundVyg;
    @JsonProperty(value = "remarks")
    private String remarks;
    @JsonProperty(value = "transit_state_descriptions")
    private List<LanguageDescription> transitStateDescriptions;

}
