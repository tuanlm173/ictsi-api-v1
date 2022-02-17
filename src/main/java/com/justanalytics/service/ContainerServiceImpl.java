package com.justanalytics.service;

import com.justanalytics.constant.ImportContainerBaseCondition;
import com.justanalytics.dto.*;
import com.justanalytics.query.Query;
import com.justanalytics.repository.DataRepository;
import com.justanalytics.types.ContainerImped;
import com.justanalytics.types.ContainerType;
import com.justanalytics.utils.QueryBuilder;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.justanalytics.constant.ContainerBaseCondition.*;
import static com.justanalytics.constant.EmptyContainerBaseCondition.*;
import static com.justanalytics.constant.ExportContainerBaseCondition.*;
import static com.justanalytics.constant.ImportContainerBaseCondition.*;

@Service
public class ContainerServiceImpl implements ContainerService {

    Logger logger = LoggerFactory.getLogger(ContainerServiceImpl.class);

    private static final String DEFAULT_CONDITION = "1=1";
    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String currentTime = "'" + LocalDateTime.now().minusDays(180).format(localDateTimeFormatter) + "Z'"; // DEV: 90 PROD: 45
    //TODO: define prune date function for each terminal, input: facility-id parameter (e.g: if MICTSI then xxx days, if SBITC then yyy days...)

    @Autowired
    private DataRepository dataRepository;

    private String filterLastVisitFlag(String lastVisitFlag) {
        String results = "1=1";
        if (lastVisitFlag != null && !lastVisitFlag.isBlank()) {
            if (lastVisitFlag.equalsIgnoreCase("true")) {
                results = "c.last_visit_flag = 1";
            }
        }
        return results;
    }

    private String parseParams(String params) {
        if (params != null && !params.isBlank())
            return String.join(", ",
                    Arrays.stream(params.split(","))
                            .map(element -> ("'" + element + "'"))
                            .collect(Collectors.toList()));
        else return "";

    }

    private String buildFilter(String filter, String input) {
        if (input != null && !input.isBlank())
            return String.format(filter, input);
        else return "";
    }

    private String buildExportShipperFilter(String filter, String input) {
        if (input != null && !input.isBlank()) {
            return String.format(filter, input, input);
        }
        else return "";
    }

//    private String buildImportShipperFilter(String filter, String input) {
//        if (input != null && !input.isBlank()) {
//            return String.format(filter, input, input, input, input);
//        }
//        else return "";
//    }

    private String buildSimpleCosmosArrayContainSearch(String filter, String input) {
        if (input != null && !input.isBlank()) {
            String[] searchInputs = input.split(",");
            List<String> results = new ArrayList<>();
            for (String searchInput : searchInputs) {
                results.add(String.format(filter, searchInput));
            }
            return "(" + String.join(" OR ", results) + ")";
        }
        else return "";
    }

//    private String buildHouseShipperConsigneeFilter(String houseShipperConsigneeFilter, String input) {
//        if (input != null && !input.isBlank()) {
//            String[] shippers = input.split(",");
//            List<String> results = new ArrayList<>();
//            for (String shipper : shippers) {
//                results.add(String.format(houseShipperConsigneeFilter, shipper));
//            }
//            return "(" + String.join(" OR ", results) + ")";
//        }
//        else return "";
//    }
//
//    private String buildHouseBolFilter(String houseBolFilter, String input) {
//        if (input != null && !input.isBlank()) {
//            String[] bols = input.split(",");
//            List<String> results = new ArrayList<>();
//            for (String bol : bols) {
//                results.add(String.format(houseBolFilter, bol));
//            }
//            return "(" + String.join(" OR ", results) + ")";
//        }
//        else return "";
//    }

    private String buildGenericShipperConsigneeFilter(String masterShipperFilter,
                                                      String masterConsigneeFilter,
                                                      String houseShipperFilter,
                                                      String houseConsigneeFilter,
                                                      String input) {
        if (input != null && !input.isBlank()) {
            List<String> results = new ArrayList<>();
            results.add(buildFilter(masterShipperFilter, parseParams(input)));
            results.add(buildFilter(masterConsigneeFilter, parseParams(input)));
            results.add(buildSimpleCosmosArrayContainSearch(houseShipperFilter, parseParams(input)));
            results.add(buildSimpleCosmosArrayContainSearch(houseConsigneeFilter, parseParams(input)));
            return "(" + String.join(" OR ", results) + ")";
        }
        else return "";
    }

    private String buildGenericBolFilter(String masterBolFilter, String houseBolFilter, String input) {
        if (input != null && !input.isBlank()) {
            List<String> results = new ArrayList<>();
            results.add(buildFilter(masterBolFilter, parseParams(input)));
            results.add(buildSimpleCosmosArrayContainSearch(houseBolFilter, parseParams(input)));
            return "(" + String.join(" OR ", results) + ")";
        }
        else return "";
    }

    private String buildBolFilter(String filter, String inputBol) {
        if (inputBol != null && !inputBol.isBlank())
            return String.format(filter, inputBol, inputBol);
        else return "";
    }

    private String buildBolNbrParam(String filter, String inputBolFields) {
        if (inputBolFields != null && !inputBolFields.isBlank()) {
            String[] bols = inputBolFields.split(",");
            List<String> results = new ArrayList<>();
            for (String bol : bols) {
                results.add(String.format(filter, bol, bol));
            }
            return "(" + String.join(" OR ", results) + ")";
        }
        else return DEFAULT_CONDITION;

    }

    private String buildSimpleTimeframeContainerParam(String filter, LocalDateTime from, LocalDateTime to) {
        if ((from != null && !from.toString().isBlank()) && (to != null && !to.toString().isBlank())) {
            return String.format(filter, from.format(iso_formatter) + 'Z', to.format(iso_formatter) + 'Z');
        }
        return DEFAULT_CONDITION;
    }

    private List<ContainerDto> getContainerDto(List<JSONObject> rawData) {

        List<ContainerDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String operatorId = String.valueOf(data.get("operator_id"));
            String complexId = String.valueOf(data.get("complex_id"));
            String facilityId = String.valueOf(data.get("facility_id"));
            String visitState = String.valueOf(data.get("visit_state"));
            String containerNbr = String.valueOf(data.get("container_nbr"));
            String equipmentType = String.valueOf(data.get("equipment_type"));
            Float teu =  Objects.nonNull(data.get("teu")) ? Float.parseFloat(String.valueOf(data.get("teu"))) : null;
            String operatorLineId = String.valueOf(data.get("line_operator_id"));
            String operatorName = String.valueOf(data.get("line_operator_name"));
            String createTime = String.valueOf(data.get("create_time"));
            String category = String.valueOf(data.get("category"));
            String freightKind = String.valueOf(data.get("freight_kind"));
            Float goodsAndCtrWtKg = Objects.nonNull(data.get("goods_and_ctr_wt_kg")) ? Float.parseFloat(String.valueOf(data.get("goods_and_ctr_wt_kg"))) : null;
            Float goodsCtrWtKgAdvised = Objects.nonNull(data.get("goods_ctr_wt_kg_advised")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_advised"))) : null;
            Float goodsCtrWtKgGateMeasured = Objects.nonNull(data.get("goods_ctr_wt_kg_gate_measured")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_gate_measured"))) : null;
            Float goodsCtrWtKgYardMeasured = Objects.nonNull(data.get("goods_ctr_wt_kg_yard_measured")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_yard_measured"))) : null;
            String sealNbr1 = String.valueOf(data.get("seal_nbr1"));
            String sealNbr2 = String.valueOf(data.get("seal_nbr2"));
            String sealNbr3 = String.valueOf(data.get("seal_nbr3"));
            String sealNbr4 = String.valueOf(data.get("seal_nbr4"));
            String stoppedVessel = String.valueOf(data.get("stopped_vessel"));
            String stoppedRail = String.valueOf(data.get("stopped_rail"));
            String stoppedRoad = String.valueOf(data.get("stopped_road"));
            String impedVessel = String.valueOf(data.get("imped_vessel"));
            String impedRail = String.valueOf(data.get("imped_rail"));
            String impedRoad = String.valueOf(data.get("imped_road"));
            String arrivePosLoctype = String.valueOf(data.get("arrive_pos_loctype"));
            String arrivePosLocId = String.valueOf(data.get("arrive_pos_locid"));
            String arrivePosSlot = String.valueOf(data.get("arrive_pos_slot"));
            String lastPosLoctype = String.valueOf(data.get("last_pos_loctype"));
            String lastPosLocId = String.valueOf(data.get("last_pos_locid"));
            String lastPosSlot = String.valueOf(data.get("last_pos_slot"));
            String timeIn = String.valueOf(data.get("time_in"));
            String timeOut = String.valueOf(data.get("time_out"));
            String bookingNumber = String.valueOf(data.get("booking_number"));
            String requiresPower = String.valueOf(data.get("requires_power"));
            String timeStateChange = String.valueOf(data.get("time_state_change"));
            String pod = String.valueOf(data.get("POD"));
            String transitState = String.valueOf(data.get("transit_state"));
            String nominalLength = String.valueOf(data.get("nominal_length"));
            String reeferType = String.valueOf(data.get("reefer_type"));
            String isoGroup = String.valueOf(data.get("iso_group"));
            String masterBlNbr = String.valueOf(data.get("master_bl_nbr"));
            String origin = String.valueOf(data.get("origin"));
            String destination = String.valueOf(data.get("destination"));
            String consigneeId = String.valueOf(data.get("consignee_id"));
            String consigneeName = String.valueOf(data.get("consignee_name"));
            String shipperId = String.valueOf(data.get("shipper_id"));
            String shipperName = String.valueOf(data.get("shipper_name"));
            String houseBlNbr = String.valueOf(data.get("house_bl_nbr"));
            String cargoCategory = String.valueOf(data.get("cargo_category"));
            String cargoConsigneeId = String.valueOf(data.get("cargo_consignee_id"));
            String cargoConsigneeName = String.valueOf(data.get("cargo_consignee_name"));
            String cargoShipperId = String.valueOf(data.get("cargo_shipper_id"));
            String cargoShipperName = String.valueOf(data.get("cargo_shipper_name"));
            String cargoOrigin = String.valueOf(data.get("cargo_origin"));

            String shipperDeclaredVgm = String.valueOf(data.get("shipper_declared_vgm"));
            String terminalMeasuredVgm = String.valueOf(data.get("terminal_measured_vgm"));
            String lastFreeDay = String.valueOf(data.get("last_free_day"));
            String paidThruDay = String.valueOf(data.get("paid_thru_day"));
            String powerLastFreeDay = String.valueOf(data.get("power_last_free_day"));
            String powerPaidThruDay = String.valueOf(data.get("power_paid_thru_day"));
            String ibRegistryNbr = String.valueOf(data.get("ib_registry_nbr"));
            String obRegistryNbr = String.valueOf(data.get("ob_registry_nbr"));
            String entryNo = String.valueOf(data.get("entry_no"));
            String requiresXray = String.valueOf(data.get("requires_xray"));
            String customTag = String.valueOf(data.get("custom_tag"));
            String ibAppointmentStartDate = String.valueOf(data.get("ib_appointment_start_date"));
            String ibAppointmentEndDate = String.valueOf(data.get("ib_appointment_end_date"));
            String obAppointmentStartDate = String.valueOf(data.get("ob_appointment_start_date"));
            String obAppointmentEndDate = String.valueOf(data.get("ob_appointment_end_date"));
            String shipper = String.valueOf(data.get("shipper"));
            String consignee = String.valueOf(data.get("consignee"));
            String showTvarrivalStatus = String.valueOf(data.get("show_tvarrival_status"));
            String tvArrivalStatus = String.valueOf(data.get("tv_arrival_status"));
            String tvArrivalRemarks = String.valueOf(data.get("tv_arrival_remarks"));
            String ibId = String.valueOf(data.get("ib_id"));
            String ibCvMode = String.valueOf(data.get("ib_cv_mode"));
            String ibCarrierName = String.valueOf(data.get("ib_carrier_name"));
            String ibOperatorName = String.valueOf(data.get("ib_operator_name"));
            String ibInboundVyg = String.valueOf(data.get("ib_inbound_vyg"));
            String ibOutboundVyg = String.valueOf(data.get("ib_outbound_vyg"));
            String obId = String.valueOf(data.get("ob_id"));
            String obCvMode = String.valueOf(data.get("ob_cv_mode"));
            String obCarrierName = String.valueOf(data.get("ob_carrier_name"));
            String obCarrierOperatorName = String.valueOf(data.get("ob_carrier_operator_name"));
            String obInboundVyg = String.valueOf(data.get("ob_inbound_vyg"));
            String obOutboundVyg = String.valueOf(data.get("ob_outbound_vyg"));
            String remarks = String.valueOf(data.get("remarks"));

            List<HouseBillOfLadings> houseBillOfLadings = new ArrayList<>();
            List<HouseBillOfLadings> rawHouseBillOfLadings = (List<HouseBillOfLadings>) data.get("house_bls");
            if (rawHouseBillOfLadings != null) houseBillOfLadings = rawHouseBillOfLadings;

            List<LanguageDescription> transitStateDescriptions = new ArrayList<>();
            List<LanguageDescription> rawTransitStateDescriptions = (List<LanguageDescription>) data.get("transit_state_descriptions");
            if (rawTransitStateDescriptions != null) transitStateDescriptions = rawTransitStateDescriptions;


            results.add(ContainerDto.builder()
                    .uniqueKey(uniqueKey)
                    .operatorId(operatorId)
                    .complexId(complexId)
                    .facilityId(facilityId)
                    .visitState(visitState)
                    .containerNbr(containerNbr)
                    .equipmentType(equipmentType)
                    .teu(teu)
                    .operatorLineId(operatorLineId)
                    .operatorName(operatorName)
                    .createTime(createTime)
                    .category(category)
                    .freightKind(freightKind)
                    .goodsAndCtrWtKg(goodsAndCtrWtKg)
                    .goodsCtrWtKgAdvised(goodsCtrWtKgAdvised)
                    .goodsCtrWtKgGateMeasured(goodsCtrWtKgGateMeasured)
                    .goodsCtrWtKgYardMeasured(goodsCtrWtKgYardMeasured)
                    .sealNbr1(sealNbr1)
                    .sealNbr2(sealNbr2)
                    .sealNbr3(sealNbr3)
                    .sealNbr4(sealNbr4)
                    .stoppedVessel(stoppedVessel)
                    .stoppedRail(stoppedRail)
                    .stoppedRoad(stoppedRoad)
                    .impedVessel(impedVessel)
                    .impedRail(impedRail)
                    .impedRoad(impedRoad)
                    .arrivePosLoctype(arrivePosLoctype)
                    .arrivePosLocId(arrivePosLocId)
                    .arrivePosSlot(arrivePosSlot)
                    .lastPosLoctype(lastPosLoctype)
                    .lastPosLocId(lastPosLocId)
                    .lastPosSlot(lastPosSlot)
                    .timeIn(timeIn)
                    .timeOut(timeOut)
                    .bookingNumber(bookingNumber)
                    .requiresPower(requiresPower)
                    .timeStateChange(timeStateChange)
                    .pod(pod)
                    .transitState(transitState)
                    .nominalLength(nominalLength)
                    .reeferType(reeferType)
                    .isoGroup(isoGroup)
                    .masterBlNbr(masterBlNbr)
                    .origin(origin)
                    .destination(destination)
                    .consigneeId(consigneeId)
                    .consigneeName(consigneeName)
                    .shipperId(shipperId)
                    .shipperName(shipperName)
                    .houseBlNbr(houseBlNbr)
                    .cargoCategory(cargoCategory)
                    .cargoConsigneeId(cargoConsigneeId)
                    .cargoConsigneeName(cargoConsigneeName)
                    .cargoShipperId(cargoShipperId)
                    .cargoShipperName(cargoShipperName)
                    .cargoOrigin(cargoOrigin)
                    .shipperDeclaredVgm(shipperDeclaredVgm)
                    .terminalMeasuredVgm(terminalMeasuredVgm)
                    .lastFreeDay(lastFreeDay)
                    .paidThruDay(paidThruDay)
                    .powerLastFreeDay(powerLastFreeDay)
                    .powerPaidThruDay(powerPaidThruDay)
                    .ibRegistryNbr(ibRegistryNbr)
                    .obRegistryNbr(obRegistryNbr)
                    .entryNo(entryNo)
                    .requiresXray(requiresXray)
                    .customTag(customTag)
                    .ibAppointmentStartDate(ibAppointmentStartDate)
                    .ibAppointmentEndDate(ibAppointmentEndDate)
                    .obAppointmentStartDate(obAppointmentStartDate)
                    .obAppointmentEndDate(obAppointmentEndDate)
                    .shipper(shipper)
                    .consignee(consignee)
                    .showTvarrivalStatus(showTvarrivalStatus)
                    .tvArrivalStatus(tvArrivalStatus)
                    .tvArrivalRemarks(tvArrivalRemarks)
                    .houseBls(houseBillOfLadings)
                    .ibId(ibId)
                    .ibCvMode(ibCvMode)
                    .ibCarrierName(ibCarrierName)
                    .ibOperatorName(ibOperatorName)
                    .ibInboundVyg(ibInboundVyg)
                    .ibOutboundVyg(ibOutboundVyg)
                    .obId(obId)
                    .obCvMode(obCvMode)
                    .obCarrierName(obCarrierName)
                    .obCarrierOperatorName(obCarrierOperatorName)
                    .obInboundVyg(obInboundVyg)
                    .obOutboundVyg(obOutboundVyg)
                    .remarks(remarks)
                    .transitStateDescriptions(transitStateDescriptions)
                    .build());

        }

        return results;
    }

    private List<EmptyContainerDto> getEmptyContainerDto(List<JSONObject> rawData) {

        List<EmptyContainerDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String operatorId = String.valueOf(data.get("operator_id"));
            String complexId = String.valueOf(data.get("complex_id"));
            String facilityId = String.valueOf(data.get("facility_id"));
            String visitState = String.valueOf(data.get("visit_state"));
            String containerNbr = String.valueOf(data.get("container_nbr"));
            String equipmentType = String.valueOf(data.get("equipment_type"));
            Float teu =  Objects.nonNull(data.get("teu")) ? Float.parseFloat(String.valueOf(data.get("teu"))) : null;
            String operatorLineId = String.valueOf(data.get("line_operator_id"));
            String operatorName = String.valueOf(data.get("line_operator_name"));
            String createTime = String.valueOf(data.get("create_time"));
            String category = String.valueOf(data.get("category"));
            String freightKind = String.valueOf(data.get("freight_kind"));
            Float goodsAndCtrWtKg = Objects.nonNull(data.get("goods_and_ctr_wt_kg")) ? Float.parseFloat(String.valueOf(data.get("goods_and_ctr_wt_kg"))) : null;
            Float goodsCtrWtKgAdvised = Objects.nonNull(data.get("goods_ctr_wt_kg_advised")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_advised"))) : null;
            Float goodsCtrWtKgGateMeasured = Objects.nonNull(data.get("goods_ctr_wt_kg_gate_measured")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_gate_measured"))) : null;
            Float goodsCtrWtKgYardMeasured = Objects.nonNull(data.get("goods_ctr_wt_kg_yard_measured")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_yard_measured"))) : null;
            String sealNbr1 = String.valueOf(data.get("seal_nbr1"));
            String sealNbr2 = String.valueOf(data.get("seal_nbr2"));
            String sealNbr3 = String.valueOf(data.get("seal_nbr3"));
            String sealNbr4 = String.valueOf(data.get("seal_nbr4"));
            String stoppedVessel = String.valueOf(data.get("stopped_vessel"));
            String stoppedRail = String.valueOf(data.get("stopped_rail"));
            String stoppedRoad = String.valueOf(data.get("stopped_road"));
            String impedVessel = String.valueOf(data.get("imped_vessel"));
            String impedRail = String.valueOf(data.get("imped_rail"));
            String impedRoad = String.valueOf(data.get("imped_road"));
            String arrivePosLoctype = String.valueOf(data.get("arrive_pos_loctype"));
            String arrivePosLocId = String.valueOf(data.get("arrive_pos_locid"));
            String arrivePosSlot = String.valueOf(data.get("arrive_pos_slot"));
            String lastPosLoctype = String.valueOf(data.get("last_pos_loctype"));
            String lastPosLocId = String.valueOf(data.get("last_pos_locid"));
            String lastPosSlot = String.valueOf(data.get("last_pos_slot"));
            String timeIn = String.valueOf(data.get("time_in"));
            String timeOut = String.valueOf(data.get("time_out"));
            String bookingNumber = String.valueOf(data.get("booking_number"));
            String requiresPower = String.valueOf(data.get("requires_power"));
            String timeStateChange = String.valueOf(data.get("time_state_change"));
            String pod = String.valueOf(data.get("POD"));
            String transitState = String.valueOf(data.get("transit_state"));
            String nominalLength = String.valueOf(data.get("nominal_length"));
            String reeferType = String.valueOf(data.get("reefer_type"));
            String isoGroup = String.valueOf(data.get("iso_group"));
            String masterBlNbr = String.valueOf(data.get("master_bl_nbr"));
            String origin = String.valueOf(data.get("origin"));
            String destination = String.valueOf(data.get("destination"));
            String consigneeId = String.valueOf(data.get("consignee_id"));
            String consigneeName = String.valueOf(data.get("consignee_name"));
            String shipperId = String.valueOf(data.get("shipper_id"));
            String shipperName = String.valueOf(data.get("shipper_name"));
            String houseBlNbr = String.valueOf(data.get("house_bl_nbr"));
            String cargoCategory = String.valueOf(data.get("cargo_category"));
            String cargoConsigneeId = String.valueOf(data.get("cargo_consignee_id"));
            String cargoConsigneeName = String.valueOf(data.get("cargo_consignee_name"));
            String cargoShipperId = String.valueOf(data.get("cargo_shipper_id"));
            String cargoShipperName = String.valueOf(data.get("cargo_shipper_name"));
            String cargoOrigin = String.valueOf(data.get("cargo_origin"));

            String shipperDeclaredVgm = String.valueOf(data.get("shipper_declared_vgm"));
            String terminalMeasuredVgm = String.valueOf(data.get("terminal_measured_vgm"));
            String lastFreeDay = String.valueOf(data.get("last_free_day"));
            String paidThruDay = String.valueOf(data.get("paid_thru_day"));
            String powerLastFreeDay = String.valueOf(data.get("power_last_free_day"));
            String powerPaidThruDay = String.valueOf(data.get("power_paid_thru_day"));
            String ibRegistryNbr = String.valueOf(data.get("ib_registry_nbr"));
            String obRegistryNbr = String.valueOf(data.get("ob_registry_nbr"));
            String entryNo = String.valueOf(data.get("entry_no"));
            String requiresXray = String.valueOf(data.get("requires_xray"));
            String customTag = String.valueOf(data.get("custom_tag"));
            String ibAppointmentStartDate = String.valueOf(data.get("ib_appointment_start_date"));
            String ibAppointmentEndDate = String.valueOf(data.get("ib_appointment_end_date"));
            String obAppointmentStartDate = String.valueOf(data.get("ob_appointment_start_date"));
            String obAppointmentEndDate = String.valueOf(data.get("ob_appointment_end_date"));
            String shipper = String.valueOf(data.get("shipper"));
            String consignee = String.valueOf(data.get("consignee"));
            String showTvarrivalStatus = String.valueOf(data.get("show_tvarrival_status"));
            String tvArrivalStatus = String.valueOf(data.get("tv_arrival_status"));
            String tvArrivalRemarks = String.valueOf(data.get("tv_arrival_remarks"));
            String ibId = String.valueOf(data.get("ib_id"));
            String ibCvMode = String.valueOf(data.get("ib_cv_mode"));
            String ibCarrierName = String.valueOf(data.get("ib_carrier_name"));
            String ibOperatorName = String.valueOf(data.get("ib_operator_name"));
            String ibInboundVyg = String.valueOf(data.get("ib_inbound_vyg"));
            String ibOutboundVyg = String.valueOf(data.get("ib_outbound_vyg"));
            String obId = String.valueOf(data.get("ob_id"));
            String obCvMode = String.valueOf(data.get("ob_cv_mode"));
            String obCarrierName = String.valueOf(data.get("ob_carrier_name"));
            String obCarrierOperatorName = String.valueOf(data.get("ob_carrier_operator_name"));
            String obInboundVyg = String.valueOf(data.get("ob_inbound_vyg"));
            String obOutboundVyg = String.valueOf(data.get("ob_outbound_vyg"));
            String remarks = String.valueOf(data.get("remarks"));

            List<HouseBillOfLadings> houseBillOfLadings = new ArrayList<>();
            List<HouseBillOfLadings> rawHouseBillOfLadings = (List<HouseBillOfLadings>) data.get("house_bls");
            if (rawHouseBillOfLadings != null) houseBillOfLadings = rawHouseBillOfLadings;

            List<LanguageDescription> transitStateDescriptions = new ArrayList<>();
            List<LanguageDescription> rawTransitStateDescriptions = (List<LanguageDescription>) data.get("transit_state_descriptions");
            if (rawTransitStateDescriptions != null) transitStateDescriptions = rawTransitStateDescriptions;


            results.add(EmptyContainerDto.builder()
                    .uniqueKey(uniqueKey)
                    .operatorId(operatorId)
                    .complexId(complexId)
                    .facilityId(facilityId)
                    .visitState(visitState)
                    .containerNbr(containerNbr)
                    .equipmentType(equipmentType)
                    .teu(teu)
                    .operatorLineId(operatorLineId)
                    .operatorName(operatorName)
                    .createTime(createTime)
                    .category(category)
                    .freightKind(freightKind)
                    .goodsAndCtrWtKg(goodsAndCtrWtKg)
                    .goodsCtrWtKgAdvised(goodsCtrWtKgAdvised)
                    .goodsCtrWtKgGateMeasured(goodsCtrWtKgGateMeasured)
                    .goodsCtrWtKgYardMeasured(goodsCtrWtKgYardMeasured)
                    .sealNbr1(sealNbr1)
                    .sealNbr2(sealNbr2)
                    .sealNbr3(sealNbr3)
                    .sealNbr4(sealNbr4)
                    .stoppedVessel(stoppedVessel)
                    .stoppedRail(stoppedRail)
                    .stoppedRoad(stoppedRoad)
                    .impedVessel(impedVessel)
                    .impedRail(impedRail)
                    .impedRoad(impedRoad)
                    .arrivePosLoctype(arrivePosLoctype)
                    .arrivePosLocId(arrivePosLocId)
                    .arrivePosSlot(arrivePosSlot)
                    .lastPosLoctype(lastPosLoctype)
                    .lastPosLocId(lastPosLocId)
                    .lastPosSlot(lastPosSlot)
                    .timeIn(timeIn)
                    .timeOut(timeOut)
                    .bookingNumber(bookingNumber)
                    .requiresPower(requiresPower)
                    .timeStateChange(timeStateChange)
                    .pod(pod)
                    .transitState(transitState)
                    .nominalLength(nominalLength)
                    .reeferType(reeferType)
                    .isoGroup(isoGroup)
                    .masterBlNbr(masterBlNbr)
                    .origin(origin)
                    .destination(destination)
                    .consigneeId(consigneeId)
                    .consigneeName(consigneeName)
                    .shipperId(shipperId)
                    .shipperName(shipperName)
                    .houseBlNbr(houseBlNbr)
                    .cargoCategory(cargoCategory)
                    .cargoConsigneeId(cargoConsigneeId)
                    .cargoConsigneeName(cargoConsigneeName)
                    .cargoShipperId(cargoShipperId)
                    .cargoShipperName(cargoShipperName)
                    .cargoOrigin(cargoOrigin)
                    .shipperDeclaredVgm(shipperDeclaredVgm)
                    .terminalMeasuredVgm(terminalMeasuredVgm)
                    .lastFreeDay(lastFreeDay)
                    .paidThruDay(paidThruDay)
                    .powerLastFreeDay(powerLastFreeDay)
                    .powerPaidThruDay(powerPaidThruDay)
                    .ibRegistryNbr(ibRegistryNbr)
                    .obRegistryNbr(obRegistryNbr)
                    .entryNo(entryNo)
                    .requiresXray(requiresXray)
                    .customTag(customTag)
                    .ibAppointmentStartDate(ibAppointmentStartDate)
                    .ibAppointmentEndDate(ibAppointmentEndDate)
                    .obAppointmentStartDate(obAppointmentStartDate)
                    .obAppointmentEndDate(obAppointmentEndDate)
                    .shipper(shipper)
                    .consignee(consignee)
                    .showTvarrivalStatus(showTvarrivalStatus)
                    .tvArrivalStatus(tvArrivalStatus)
                    .tvArrivalRemarks(tvArrivalRemarks)
                    .houseBls(houseBillOfLadings)
                    .ibId(ibId)
                    .ibCvMode(ibCvMode)
                    .ibCarrierName(ibCarrierName)
                    .ibOperatorName(ibOperatorName)
                    .ibInboundVyg(ibInboundVyg)
                    .ibOutboundVyg(ibOutboundVyg)
                    .obId(obId)
                    .obCvMode(obCvMode)
                    .obCarrierName(obCarrierName)
                    .obCarrierOperatorName(obCarrierOperatorName)
                    .obInboundVyg(obInboundVyg)
                    .obOutboundVyg(obOutboundVyg)
                    .remarks(remarks)
                    .transitStateDescriptions(transitStateDescriptions)
                    .build());

        }

        return results;
    }

    private List<ExportContainerDto> getExportContainerDto(List<JSONObject> rawData) {

        List<ExportContainerDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("unique_key"));
            String operatorId = String.valueOf(data.get("operator_id"));
            String complexId = String.valueOf(data.get("complex_id"));
            String facilityId = String.valueOf(data.get("facility_id"));
            String visitState = String.valueOf(data.get("visit_state"));
            String containerNbr = String.valueOf(data.get("container_nbr"));
            String equipmentType = String.valueOf(data.get("equipment_type"));
            Float teu =  Objects.nonNull(data.get("teu")) ? Float.parseFloat(String.valueOf(data.get("teu"))) : null;
            String operatorLineId = String.valueOf(data.get("line_operator_id"));
            String operatorName = String.valueOf(data.get("line_operator_name"));
            String createTime = String.valueOf(data.get("create_time"));
            String category = String.valueOf(data.get("category"));
            String freightKind = String.valueOf(data.get("freight_kind"));
            Float goodsAndCtrWtKg = Objects.nonNull(data.get("goods_and_ctr_wt_kg")) ? Float.parseFloat(String.valueOf(data.get("goods_and_ctr_wt_kg"))) : null;
            Float goodsCtrWtKgAdvised = Objects.nonNull(data.get("goods_ctr_wt_kg_advised")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_advised"))) : null;
            Float goodsCtrWtKgGateMeasured = Objects.nonNull(data.get("goods_ctr_wt_kg_gate_measured")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_gate_measured"))) : null;
            Float goodsCtrWtKgYardMeasured = Objects.nonNull(data.get("goods_ctr_wt_kg_yard_measured")) ? Float.parseFloat(String.valueOf(data.get("goods_ctr_wt_kg_yard_measured"))) : null;
            String sealNbr1 = String.valueOf(data.get("seal_nbr1"));
            String sealNbr2 = String.valueOf(data.get("seal_nbr2"));
            String sealNbr3 = String.valueOf(data.get("seal_nbr3"));
            String sealNbr4 = String.valueOf(data.get("seal_nbr4"));
            String stoppedVessel = String.valueOf(data.get("stopped_vessel"));
            String stoppedRail = String.valueOf(data.get("stopped_rail"));
            String stoppedRoad = String.valueOf(data.get("stopped_road"));
            String impedVessel = String.valueOf(data.get("imped_vessel"));
            String impedRail = String.valueOf(data.get("imped_rail"));
            String impedRoad = String.valueOf(data.get("imped_road"));
            String arrivePosLoctype = String.valueOf(data.get("arrive_pos_loctype"));
            String arrivePosLocId = String.valueOf(data.get("arrive_pos_locid"));
            String arrivePosSlot = String.valueOf(data.get("arrive_pos_slot"));
            String lastPosLoctype = String.valueOf(data.get("last_pos_loctype"));
            String lastPosLocId = String.valueOf(data.get("last_pos_locid"));
            String lastPosSlot = String.valueOf(data.get("last_pos_slot"));
            String timeIn = String.valueOf(data.get("time_in"));
            String timeOut = String.valueOf(data.get("time_out"));
            String bookingNumber = String.valueOf(data.get("booking_number"));
            String requiresPower = String.valueOf(data.get("requires_power"));
            String timeStateChange = String.valueOf(data.get("time_state_change"));
            String pod = String.valueOf(data.get("POD"));
            String transitState = String.valueOf(data.get("transit_state"));
            String nominalLength = String.valueOf(data.get("nominal_length"));
            String reeferType = String.valueOf(data.get("reefer_type"));
            String isoGroup = String.valueOf(data.get("iso_group"));
            String masterBlNbr = String.valueOf(data.get("master_bl_nbr"));
            String origin = String.valueOf(data.get("origin"));
            String destination = String.valueOf(data.get("destination"));
            String consigneeId = String.valueOf(data.get("consignee_id"));
            String consigneeName = String.valueOf(data.get("consignee_name"));
            String shipperId = String.valueOf(data.get("shipper_id"));
            String shipperName = String.valueOf(data.get("shipper_name"));
            String houseBlNbr = String.valueOf(data.get("house_bl_nbr"));
            String cargoCategory = String.valueOf(data.get("cargo_category"));
            String cargoConsigneeId = String.valueOf(data.get("cargo_consignee_id"));
            String cargoConsigneeName = String.valueOf(data.get("cargo_consignee_name"));
            String cargoShipperId = String.valueOf(data.get("cargo_shipper_id"));
            String cargoShipperName = String.valueOf(data.get("cargo_shipper_name"));
            String cargoOrigin = String.valueOf(data.get("cargo_origin"));

            String shipperDeclaredVgm = String.valueOf(data.get("shipper_declared_vgm"));
            String terminalMeasuredVgm = String.valueOf(data.get("terminal_measured_vgm"));
            String lastFreeDay = String.valueOf(data.get("last_free_day"));
            String paidThruDay = String.valueOf(data.get("paid_thru_day"));
            String powerLastFreeDay = String.valueOf(data.get("power_last_free_day"));
            String powerPaidThruDay = String.valueOf(data.get("power_paid_thru_day"));
            String ibRegistryNbr = String.valueOf(data.get("ib_registry_nbr"));
            String obRegistryNbr = String.valueOf(data.get("ob_registry_nbr"));
            String entryNo = String.valueOf(data.get("entry_no"));
            String requiresXray = String.valueOf(data.get("requires_xray"));
            String customTag = String.valueOf(data.get("custom_tag"));
            String ibAppointmentStartDate = String.valueOf(data.get("ib_appointment_start_date"));
            String ibAppointmentEndDate = String.valueOf(data.get("ib_appointment_end_date"));
            String obAppointmentStartDate = String.valueOf(data.get("ob_appointment_start_date"));
            String obAppointmentEndDate = String.valueOf(data.get("ob_appointment_end_date"));
            String shipper = String.valueOf(data.get("shipper"));
            String consignee = String.valueOf(data.get("consignee"));
            String showTvarrivalStatus = String.valueOf(data.get("show_tvarrival_status"));
            String tvArrivalStatus = String.valueOf(data.get("tv_arrival_status"));
            String tvArrivalRemarks = String.valueOf(data.get("tv_arrival_remarks"));
            String ibId = String.valueOf(data.get("ib_id"));
            String ibCvMode = String.valueOf(data.get("ib_cv_mode"));
            String ibCarrierName = String.valueOf(data.get("ib_carrier_name"));
            String ibOperatorName = String.valueOf(data.get("ib_operator_name"));
            String ibInboundVyg = String.valueOf(data.get("ib_inbound_vyg"));
            String ibOutboundVyg = String.valueOf(data.get("ib_outbound_vyg"));
            String obId = String.valueOf(data.get("ob_id"));
            String obCvMode = String.valueOf(data.get("ob_cv_mode"));
            String obCarrierName = String.valueOf(data.get("ob_carrier_name"));
            String obCarrierOperatorName = String.valueOf(data.get("ob_carrier_operator_name"));
            String obInboundVyg = String.valueOf(data.get("ob_inbound_vyg"));
            String obOutboundVyg = String.valueOf(data.get("ob_outbound_vyg"));
            String remarks = String.valueOf(data.get("remarks"));

            List<HouseBillOfLadings> houseBillOfLadings = new ArrayList<>();
            List<HouseBillOfLadings> rawHouseBillOfLadings = (List<HouseBillOfLadings>) data.get("house_bls");
            if (rawHouseBillOfLadings != null) houseBillOfLadings = rawHouseBillOfLadings;

            List<LanguageDescription> transitStateDescriptions = new ArrayList<>();
            List<LanguageDescription> rawTransitStateDescriptions = (List<LanguageDescription>) data.get("transit_state_descriptions");
            if (rawTransitStateDescriptions != null) transitStateDescriptions = rawTransitStateDescriptions;


            results.add(ExportContainerDto.builder()
                    .uniqueKey(uniqueKey)
                    .operatorId(operatorId)
                    .complexId(complexId)
                    .facilityId(facilityId)
                    .visitState(visitState)
                    .containerNbr(containerNbr)
                    .equipmentType(equipmentType)
                    .teu(teu)
                    .operatorLineId(operatorLineId)
                    .operatorName(operatorName)
                    .createTime(createTime)
                    .category(category)
                    .freightKind(freightKind)
                    .goodsAndCtrWtKg(goodsAndCtrWtKg)
                    .goodsCtrWtKgAdvised(goodsCtrWtKgAdvised)
                    .goodsCtrWtKgGateMeasured(goodsCtrWtKgGateMeasured)
                    .goodsCtrWtKgYardMeasured(goodsCtrWtKgYardMeasured)
                    .sealNbr1(sealNbr1)
                    .sealNbr2(sealNbr2)
                    .sealNbr3(sealNbr3)
                    .sealNbr4(sealNbr4)
                    .stoppedVessel(stoppedVessel)
                    .stoppedRail(stoppedRail)
                    .stoppedRoad(stoppedRoad)
                    .impedVessel(impedVessel)
                    .impedRail(impedRail)
                    .impedRoad(impedRoad)
                    .arrivePosLoctype(arrivePosLoctype)
                    .arrivePosLocId(arrivePosLocId)
                    .arrivePosSlot(arrivePosSlot)
                    .lastPosLoctype(lastPosLoctype)
                    .lastPosLocId(lastPosLocId)
                    .lastPosSlot(lastPosSlot)
                    .timeIn(timeIn)
                    .timeOut(timeOut)
                    .bookingNumber(bookingNumber)
                    .requiresPower(requiresPower)
                    .timeStateChange(timeStateChange)
                    .pod(pod)
                    .transitState(transitState)
                    .nominalLength(nominalLength)
                    .reeferType(reeferType)
                    .isoGroup(isoGroup)
                    .masterBlNbr(masterBlNbr)
                    .origin(origin)
                    .destination(destination)
                    .consigneeId(consigneeId)
                    .consigneeName(consigneeName)
                    .shipperId(shipperId)
                    .shipperName(shipperName)
                    .houseBlNbr(houseBlNbr)
                    .cargoCategory(cargoCategory)
                    .cargoConsigneeId(cargoConsigneeId)
                    .cargoConsigneeName(cargoConsigneeName)
                    .cargoShipperId(cargoShipperId)
                    .cargoShipperName(cargoShipperName)
                    .cargoOrigin(cargoOrigin)
                    .shipperDeclaredVgm(shipperDeclaredVgm)
                    .terminalMeasuredVgm(terminalMeasuredVgm)
                    .lastFreeDay(lastFreeDay)
                    .paidThruDay(paidThruDay)
                    .powerLastFreeDay(powerLastFreeDay)
                    .powerPaidThruDay(powerPaidThruDay)
                    .ibRegistryNbr(ibRegistryNbr)
                    .obRegistryNbr(obRegistryNbr)
                    .entryNo(entryNo)
                    .requiresXray(requiresXray)
                    .customTag(customTag)
                    .ibAppointmentStartDate(ibAppointmentStartDate)
                    .ibAppointmentEndDate(ibAppointmentEndDate)
                    .obAppointmentStartDate(obAppointmentStartDate)
                    .obAppointmentEndDate(obAppointmentEndDate)
                    .shipper(shipper)
                    .consignee(consignee)
                    .showTvarrivalStatus(showTvarrivalStatus)
                    .tvArrivalStatus(tvArrivalStatus)
                    .tvArrivalRemarks(tvArrivalRemarks)
                    .houseBls(houseBillOfLadings)
                    .ibId(ibId)
                    .ibCvMode(ibCvMode)
                    .ibCarrierName(ibCarrierName)
                    .ibOperatorName(ibOperatorName)
                    .ibInboundVyg(ibInboundVyg)
                    .ibOutboundVyg(ibOutboundVyg)
                    .obId(obId)
                    .obCvMode(obCvMode)
                    .obCarrierName(obCarrierName)
                    .obCarrierOperatorName(obCarrierOperatorName)
                    .obInboundVyg(obInboundVyg)
                    .obOutboundVyg(obOutboundVyg)
                    .remarks(remarks)
                    .transitStateDescriptions(transitStateDescriptions)
                    .build());

        }

        return results;
    }

    private List<String> buildEmptyContainerConditions(
            String facilityId,
            String containerVisitState,
            String containerTransitState,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerNumber,
            String containerEquipmentType,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerUniqueKey,
            String bolNumber,
            String bookingNumber
    ) {
        List<String> filters = new ArrayList<>();

        String containerFacilityIdFilter = buildFilter(EMPTY_CONTAINER_FACILITY, parseParams(facilityId));
        String containerVisitStateFilter = buildFilter(EMPTY_CONTAINER_VISIT_STATE, parseParams(containerVisitState));
        String containerTransitStateFilter = buildFilter(EMPTY_CONTAINER_TRANSIT_STATE, parseParams(containerTransitState));
        String containerIsoGroupFilter = buildFilter(EMPTY_CONTAINER_ISO_GROUP, parseParams(containerIsoGroup));
        String containerArrivePosLocTypeFilter = buildFilter(EMPTY_CONTAINER_ARRIVE_POS_LOCTYPE, parseParams(containerArrivePosLocType));
        String containerDepartPosLocTypeFilter = buildFilter(EMPTY_CONTAINER_DEPART_POST_LOCTYPE, parseParams(containerDepartPosLocType));
        String containerDepartPosLocIdFilter = buildFilter(EMPTY_CONTAINER_DEPART_POST_LOC_ID, parseParams(containerDepartPosLocId));
        String containerArrivePosLocIdFilter = buildFilter(EMPTY_CONTAINER_ARRIVE_POS_LOC_ID, parseParams(containerArrivePosLocId));
        String containerNumberFilter = buildFilter(EMPTY_CONTAINER_NUMBER, parseParams(containerNumber));
        String containerEquipmentTypeFilter = buildFilter(EMPTY_CONTAINER_EQUIPMENT_TYPE, parseParams(containerEquipmentType));
        String containerOperationLineIFilter = buildFilter(EMPTY_CONTAINER_OPERATION_LINE_ID, parseParams(containerOperationLineId));

        String containerTimeInFilter = buildSimpleTimeframeContainerParam(EMPTY_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String containerTimeOutFilter = buildSimpleTimeframeContainerParam(EMPTY_CONTAINER_TIME_OUT, departFrom, departTo);

        String containerUniqueKeyFilter = buildFilter(EMPTY_CONTAINER_UNIQUE_KEY, parseParams(containerUniqueKey));
        String bolNumberFilter = buildFilter(EMPTY_CONTAINER_BOL_NUMBER, parseParams(bolNumber));
        String bookingNumberFilter = buildFilter(EMPTY_CONTAINER_BOOKING_NUMBER, parseParams(bookingNumber));

        filters.add(containerFacilityIdFilter);
        filters.add(containerVisitStateFilter);
        filters.add(containerTransitStateFilter);
        filters.add(containerIsoGroupFilter);
        filters.add(containerArrivePosLocTypeFilter);
        filters.add(containerDepartPosLocTypeFilter);
        filters.add(containerDepartPosLocIdFilter);
        filters.add(containerArrivePosLocIdFilter);
        filters.add(containerNumberFilter);
        filters.add(containerEquipmentTypeFilter);
        filters.add(containerOperationLineIFilter);
        filters.add(containerTimeInFilter);
        filters.add(containerTimeOutFilter);
        filters.add(containerUniqueKeyFilter);
        filters.add(bolNumberFilter);
        filters.add(bookingNumberFilter);

        return filters;
    }

    private List<String> buildAllContainerConditions(
            String facilityId,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerNumber,
            String containerEquipmentType,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String uniqueKey,
            String bookingNumber,
            String bolNumber,
            String shipper
    ) {
        List<String> filters = new ArrayList<>();

        String containerFacilityFilter = buildFilter(ALL_CONTAINER_FACILITY, parseParams(facilityId));
        String containerVisitStateFilter = buildFilter(ALL_CONTAINER_VISIT_STATE, parseParams(containerVisitState));
        String containerTransitStateFilter = buildFilter(ALL_CONTAINER_TRANSIT_STATE, parseParams(containerTransitState));
        String containerIsoGroupFilter = buildFilter(ALL_CONTAINER_ISO_GROUP, parseParams(containerIsoGroup));
        String containerArrivePosLocTypeFilter = buildFilter(ALL_CONTAINER_ARRIVE_POS_LOCTYPE, parseParams(containerArrivePosLocType));
        String containerDepartPosLocTypeFilter = buildFilter(ALL_CONTAINER_DEPART_POST_LOCTYPE, parseParams(containerDepartPosLocType));
        String containerDepartPosLocIdFilter = buildFilter(ALL_CONTAINER_DEPART_POST_LOC_ID, parseParams(containerDepartPosLocId));
        String containerArrivePosLocIdFilter = buildFilter(ALL_CONTAINER_ARRIVE_POS_LOC_ID, parseParams(containerArrivePosLocId));
        String containerNumberFilter = buildFilter(ALL_CONTAINER_NUMBER, parseParams(containerNumber));
        String containerEquipmentTypeFilter = buildFilter(ALL_CONTAINER_EQUIPMENT_TYPE, parseParams(containerEquipmentType));
        String containerOperationLineIFilter = buildFilter(ALL_CONTAINER_OPERATION_LINE_ID, parseParams(containerOperationLineId));
        String containerBookingNumberFilter = buildFilter(ALL_CONTAINER_BOOKING_NUMBER, parseParams(bookingNumber));
        String containerFreightKindFilter = buildFilter(ALL_CONTAINER_FREIGHT_KIND, parseParams(containerFreightKind));
        String containerBolNumberFilter = buildGenericBolFilter(ALL_CONTAINER_MASTER_BOL_NUMBER, ALL_CONTAINER_HOUSE_BOL_NUMBER, bolNumber);

        String containerTimeInFilter = buildSimpleTimeframeContainerParam(ALL_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String containerTimeOutFilter = buildSimpleTimeframeContainerParam(ALL_CONTAINER_TIME_OUT, departFrom, departTo);

        String containerUniqueKeyFilter = buildFilter(ALL_CONTAINER_UNIQUE_KEY, parseParams(uniqueKey));
        String containerShipperConsigneeFilter = buildGenericShipperConsigneeFilter(
                ALL_CONTAINER_MASTER_SHIPPER,
                ALL_CONTAINER_MASTER_CONSIGNEE,
                ALL_CONTAINER_HOUSE_SHIPPER,
                ALL_CONTAINER_HOUSE_CONSIGNEE,
                shipper);

        filters.add(containerFacilityFilter);
        filters.add(containerFreightKindFilter);
        filters.add(containerVisitStateFilter);
        filters.add(containerTransitStateFilter);
        filters.add(containerIsoGroupFilter);
        filters.add(containerArrivePosLocTypeFilter);
        filters.add(containerDepartPosLocTypeFilter);
        filters.add(containerDepartPosLocIdFilter);
        filters.add(containerArrivePosLocIdFilter);
        filters.add(containerNumberFilter);
        filters.add(containerEquipmentTypeFilter);
        filters.add(containerOperationLineIFilter);
        filters.add(containerTimeInFilter);
        filters.add(containerTimeOutFilter);
        filters.add(containerUniqueKeyFilter);
        filters.add(containerShipperConsigneeFilter);
        filters.add(containerBookingNumberFilter);
        filters.add(containerBolNumberFilter);

        return filters;
    }

    private List<String> buildExportContainerConditions(
            String facilityId,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerNumber,
            String containerEquipmentType,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerBookingNumber,
            String containerUniqueKey,
            String bolNumber,
            String shipper,
            String impedType
    ) {
        List<String> filters = new ArrayList<>();

        String containerFacilityFilter = buildFilter(EXPORT_CONTAINER_FACILITY, parseParams(facilityId));
        String containerFreightKindFilter = buildFilter(EXPORT_CONTAINER_FREIGHT_KIND, parseParams(containerFreightKind));
        String containerVisitStateFilter = buildFilter(EXPORT_CONTAINER_VISIT_STATE, parseParams(containerVisitState));
        String containerTransitStateFilter = buildFilter(EXPORT_CONTAINER_TRANSIT_STATE, parseParams(containerTransitState));
        String containerIsoGroupFilter = buildFilter(EXPORT_CONTAINER_ISO_GROUP, parseParams(containerIsoGroup));
        String containerArrivePosLocTypeFilter = buildFilter(EXPORT_CONTAINER_ARRIVE_POS_LOCTYPE, parseParams(containerArrivePosLocType));
        String containerDepartPosLocTypeFilter = buildFilter(EXPORT_CONTAINER_DEPART_POST_LOCTYPE, parseParams(containerDepartPosLocType));
        String containerDepartPosLocIdFilter = buildFilter(EXPORT_CONTAINER_DEPART_POST_LOC_ID, parseParams(containerDepartPosLocId));
        String containerArrivePosLocIdFilter = buildFilter(EXPORT_CONTAINER_ARRIVE_POS_LOC_ID, parseParams(containerArrivePosLocId));
        String containerNumberFilter = buildFilter(EXPORT_CONTAINER_NUMBER, parseParams(containerNumber));
        String containerEquipmentTypeFilter = buildFilter(EXPORT_CONTAINER_EQUIPMENT_TYPE, parseParams(containerEquipmentType));
        String containerOperationLineIdFilter = buildFilter(EXPORT_CONTAINER_OPERATION_LINE_ID, parseParams(containerOperationLineId));
        String timeInFilter = buildSimpleTimeframeContainerParam(EXPORT_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String timeOutFilter = buildSimpleTimeframeContainerParam(EXPORT_CONTAINER_TIME_OUT, departFrom, departTo);
        String containerBookingNumberFilter = buildFilter(EXPORT_CONTAINER_BOOKING_NUMBER, parseParams(containerBookingNumber));

        String containerUniqueKeyFilter = buildFilter(EXPORT_CONTAINER_UNIQUE_KEY, parseParams(containerUniqueKey));
        String containerBolFilter = buildFilter(EXPORT_CONTAINER_BOL_NUMBER, parseParams(bolNumber));
        String containerShipperFilter = buildExportShipperFilter(EXPORT_CONTAINER_SHIPPER, parseParams(shipper));

        if (ContainerImped.NONE.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(EXPORT_IMPED_TYPE_NONE);
        else if (ContainerImped.ANY.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(EXPORT_IMPED_TYPE_ANY);
        else if (ContainerImped.TRUCK.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(EXPORT_IMPED_TYPE_TRUCK);
        else if (ContainerImped.VESSEL.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(EXPORT_IMPED_TYPE_VESSEL);
        else if (ContainerImped.RAIL.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(EXPORT_IMPED_TYPE_RAIL);
        else filters.add(DEFAULT_CONDITION);

        filters.add(containerFacilityFilter);
        filters.add(containerFreightKindFilter);
        filters.add(containerVisitStateFilter);
        filters.add(containerTransitStateFilter);
        filters.add(containerIsoGroupFilter);
        filters.add(containerArrivePosLocTypeFilter);
        filters.add(containerDepartPosLocTypeFilter);
        filters.add(containerDepartPosLocIdFilter);
        filters.add(containerArrivePosLocIdFilter);
        filters.add(containerNumberFilter);
        filters.add(containerEquipmentTypeFilter);
        filters.add(containerOperationLineIdFilter);
        filters.add(timeInFilter);
        filters.add(timeOutFilter);
        filters.add(containerBookingNumberFilter);
        filters.add(containerUniqueKeyFilter);
        filters.add(containerBolFilter);
        filters.add(containerShipperFilter);

        return filters;
    }

    private List<String> buildImportContainerConditions(
            String facilityId,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerNumber,
            String containerEquipmentType,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerBookingNumber,
            String containerBolNumber,
            String containerUniqueKey,
            String shipper,
            String impedType
    ) {
        List<String> filters = new ArrayList<>();

        String containerFacilityFilter = buildFilter(IMPORT_CONTAINER_FACILITY, parseParams(facilityId));
        String containerFreightKindFilter = buildFilter(IMPORT_CONTAINER_FREIGHT_KIND, parseParams(containerFreightKind));
        String containerVisitStateFilter = buildFilter(IMPORT_CONTAINER_VISIT_STATE, parseParams(containerVisitState));
        String containerTransitStateFilter = buildFilter(IMPORT_CONTAINER_TRANSIT_STATE, parseParams(containerTransitState));
        String containerIsoGroupFilter = buildFilter(IMPORT_CONTAINER_ISO_GROUP, parseParams(containerIsoGroup));
        String containerArrivePosLocTypeFilter = buildFilter(IMPORT_CONTAINER_ARRIVE_POS_LOCTYPE, parseParams(containerArrivePosLocType));
        String containerDepartPosLocTypeFilter = buildFilter(IMPORT_CONTAINER_DEPART_POST_LOCTYPE, parseParams(containerDepartPosLocType));
        String containerDepartPosLocIdFilter = buildFilter(IMPORT_CONTAINER_DEPART_POST_LOC_ID, parseParams(containerDepartPosLocId));
        String containerArrivePosLocIdFilter = buildFilter(IMPORT_CONTAINER_ARRIVE_POS_LOC_ID, parseParams(containerArrivePosLocId));
        String containerNumberFilter = buildFilter(IMPORT_CONTAINER_NUMBER, parseParams(containerNumber));
        String containerEquipmentTypeFilter = buildFilter(IMPORT_CONTAINER_EQUIPMENT_TYPE, parseParams(containerEquipmentType));
        String containerOperationLineIdFilter = buildFilter(IMPORT_CONTAINER_OPERATION_LINE_ID, parseParams(containerOperationLineId));
        String timeInFilter = buildSimpleTimeframeContainerParam(IMPORT_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String timeOutFilter = buildSimpleTimeframeContainerParam(IMPORT_CONTAINER_TIME_OUT, departFrom, departTo);
        String containerBookingNumberFilter = buildFilter(IMPORT_CONTAINER_BOOKING_NUMBER, parseParams(containerBookingNumber));
        String containerBolNumberFilter = buildGenericBolFilter(IMPORT_CONTAINER_MASTER_BOL_NUMBER, IMPORT_CONTAINER_HOUSE_BOL_NUMBER, containerBolNumber);
        String containerUniqueKeyFilter = buildFilter(IMPORT_CONTAINER_UNIQUE_KEY, parseParams(containerUniqueKey));

        String containerShipperConsigneeFilter = buildGenericShipperConsigneeFilter(
                IMPORT_CONTAINER_MASTER_SHIPPER,
                IMPORT_CONTAINER_MASTER_CONSIGNEE,
                IMPORT_CONTAINER_HOUSE_SHIPPER,
                IMPORT_CONTAINER_HOUSE_CONSIGNEE,
                shipper);

        if (ContainerImped.NONE.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(IMPORT_IMPED_TYPE_NONE);
        else if (ContainerImped.ANY.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(IMPORT_IMPED_TYPE_ANY);
        else if (ContainerImped.TRUCK.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(IMPORT_IMPED_TYPE_TRUCK);
        else if (ContainerImped.VESSEL.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(IMPORT_IMPED_TYPE_VESSEL);
        else if (ContainerImped.RAIL.getContainerImped().equalsIgnoreCase(impedType))
            filters.add(IMPORT_IMPED_TYPE_RAIL);
        else filters.add(DEFAULT_CONDITION);

        filters.add(containerFacilityFilter);
        filters.add(containerFreightKindFilter);
        filters.add(containerVisitStateFilter);
        filters.add(containerTransitStateFilter);
        filters.add(containerIsoGroupFilter);
        filters.add(containerArrivePosLocTypeFilter);
        filters.add(containerDepartPosLocTypeFilter);
        filters.add(containerDepartPosLocIdFilter);
        filters.add(containerArrivePosLocIdFilter);
        filters.add(containerNumberFilter);
        filters.add(containerEquipmentTypeFilter);
        filters.add(containerOperationLineIdFilter);
        filters.add(timeInFilter);
        filters.add(timeOutFilter);
        filters.add(containerBookingNumberFilter);
        filters.add(containerBolNumberFilter);
        filters.add(containerUniqueKeyFilter);
        filters.add(containerShipperConsigneeFilter);

        return filters;
    }

    @Override
    public List<ContainerDto> findContainer(
            Query query,
            String containerType,
            String facilityId,
            String containerNumber,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerEquipmentType,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerBookingNumber,
            String bolNumber,
            String containerUniqueKey,
            String shipper,
            String lastVisitFlag,
            String impedType,
            String operationType,
            List<String> terminalConditions
    ) {
        List<JSONObject> results = new ArrayList<>();

        if (ContainerType.IMPORT.getContainerType().equalsIgnoreCase(containerType)) {

            // Main query
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(String.format(IMPORT_CONTAINER_BASE_QUERY, filterLastVisitFlag(lastVisitFlag), currentTime));

            // Persona filter
            List<String> personaFilters = buildImportContainerConditions(
                    facilityId,
                    containerFreightKind,
                    containerVisitState,
                    containerTransitState,
                    containerIsoGroup,
                    containerArrivePosLocType,
                    containerDepartPosLocType,
                    containerDepartPosLocId,
                    containerArrivePosLocId,
                    containerNumber,
                    containerEquipmentType,
                    containerOperationLineId,
                    arriveFrom,
                    arriveTo,
                    departFrom,
                    departTo,
                    containerBookingNumber,
                    bolNumber,
                    containerUniqueKey,
                    shipper,
                    impedType
            );

            personaFilters = personaFilters.stream()
                    .filter(e -> !e.equalsIgnoreCase(""))
                    .filter(e -> !e.equalsIgnoreCase("1=1"))
                    .collect(Collectors.toList());

            if (personaFilters.size() == 0) {
                queryBuilder.append(" AND ");
            }
            else {
                queryBuilder.append(String.format(" AND %s", "(" + String.join(" " + operationType + " ", personaFilters) + ")"));
                queryBuilder.append(" AND ");
            }

            // Search filter
            QueryBuilder filterBuilder = new QueryBuilder();

            if (query.filter != null) {
                String filter = filterBuilder.buildCosmosSearchFilter(query);
                queryBuilder.append(filter);
            }
            else queryBuilder.append("1=1");

            // Terminal condition
            if(!terminalConditions.contains("ALL")) {
                queryBuilder.append(" AND ");
                List<String> conditions = new ArrayList<>();
                for (String terminalCondition : terminalConditions) {
                    conditions.add(String.format("c.facility_id = '%s'", terminalCondition));
                }
                queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
            }

            // Order
            if (!query.sort.isEmpty()) {
                String sortBy = filterBuilder.buildOrderByString(query.sort);
                queryBuilder.append(String.format(" ORDER BY %s", sortBy));
            }

            // Offset limit
            queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

            String sql = queryBuilder.toString();
            logger.info("Cosmos SQL statement: {}", sql);
            results = dataRepository.getSimpleDataFromCosmos(IMPORT_CONTAINER_NAME, sql);
        }

        else if (ContainerType.ALL.getContainerType().equalsIgnoreCase(containerType)) {

            // Main query
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(String.format(ALL_CONTAINER_BASE_QUERY, filterLastVisitFlag(lastVisitFlag), currentTime));

            // Persona filter
            List<String> personaFilters = buildAllContainerConditions(
                    facilityId,
                    containerFreightKind,
                    containerVisitState,
                    containerTransitState,
                    containerIsoGroup,
                    containerArrivePosLocType,
                    containerDepartPosLocType,
                    containerDepartPosLocId,
                    containerArrivePosLocId,
                    containerNumber,
                    containerEquipmentType,
                    containerOperationLineId,
                    arriveFrom,
                    arriveTo,
                    departFrom,
                    departTo,
                    containerUniqueKey,
                    containerBookingNumber,
                    bolNumber,
                    shipper
            );

            personaFilters = personaFilters.stream()
                    .filter(e -> !e.equalsIgnoreCase(""))
                    .filter(e -> !e.equalsIgnoreCase("1=1"))
                    .collect(Collectors.toList());

            if (personaFilters.size() == 0) {
                queryBuilder.append(" AND ");
            }
            else {
                queryBuilder.append(String.format(" AND %s", "(" + String.join(" " + operationType + " ", personaFilters) + ")"));
                queryBuilder.append(" AND ");
            }

            // Search filter
            QueryBuilder filterBuilder = new QueryBuilder();

            if (query.filter != null) {
                String filter = filterBuilder.buildCosmosSearchFilter(query);
                queryBuilder.append(filter);
            }
            else queryBuilder.append("1=1");

            // Terminal condition
            if(!terminalConditions.contains("ALL")) {
                queryBuilder.append(" AND ");
                List<String> conditions = new ArrayList<>();
                for (String terminalCondition : terminalConditions) {
                    conditions.add(String.format("c.facility_id = '%s'", terminalCondition));
                }
                queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
            }

            // Order
            if (!query.sort.isEmpty()) {
                String sortBy = filterBuilder.buildOrderByString(query.sort);
                queryBuilder.append(String.format(" ORDER BY %s", sortBy));
            }

            // Offset limit
            queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

            String sql = queryBuilder.toString();
            logger.info("Cosmos SQL statement: {}", sql);
            results = dataRepository.getSimpleDataFromCosmos(ALL_CONTAINER_NAME, sql);

        }

        return getContainerDto(results);


    }

    @Override
    public List<ExportContainerDto> findExportContainer(
            Query query,
            String containerType,
            String facilityId,
            String containerNumber,
            String containerOperationLineId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerFreightKind,
            String containerVisitState,
            String containerTransitState,
            String containerEquipmentType,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            String containerBookingNumber,
            String bolNumber,
            String containerUniqueKey,
            String shipper,
            String lastVisitFlag,
            String impedType,
            String operationType,
            List<String> terminalConditions
    ) {
        List<JSONObject> results = new ArrayList<>();

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(EXPORT_CONTAINER_BASE_QUERY, filterLastVisitFlag(lastVisitFlag), currentTime));

        // Persona filter
        List<String> personaFilters = buildExportContainerConditions(
                facilityId,
                containerFreightKind,
                containerVisitState,
                containerTransitState,
                containerIsoGroup,
                containerArrivePosLocType,
                containerDepartPosLocType,
                containerDepartPosLocId,
                containerArrivePosLocId,
                containerNumber,
                containerEquipmentType,
                containerOperationLineId,
                arriveFrom,
                arriveTo,
                departFrom,
                departTo,
                containerBookingNumber,
                containerUniqueKey,
                bolNumber,
                shipper,
                impedType
        );

        personaFilters = personaFilters.stream()
                .filter(e -> !e.equalsIgnoreCase(""))
                .filter(e -> !e.equalsIgnoreCase("1=1"))
                .collect(Collectors.toList());

        if (personaFilters.size() == 0) {
            queryBuilder.append(" AND ");
        }
        else {
            queryBuilder.append(String.format(" AND %s", "(" + String.join(" " + operationType + " ", personaFilters) + ")"));
            queryBuilder.append(" AND ");
        }

        // Search filter
        QueryBuilder filterBuilder = new QueryBuilder();

        if (query.filter != null) {
            String filter = filterBuilder.buildCosmosSearchFilter(query);
            queryBuilder.append(filter);
        }
        else queryBuilder.append("1=1");

        // Terminal condition
        if(!terminalConditions.contains("ALL")) {
            queryBuilder.append(" AND ");
            List<String> conditions = new ArrayList<>();
            for (String terminalCondition : terminalConditions) {
                conditions.add(String.format("c.facility_id = '%s'", terminalCondition));
            }
            queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
        }

        // Order
        if (!query.sort.isEmpty()) {
            String sortBy = filterBuilder.buildOrderByString(query.sort);
            queryBuilder.append(String.format(" ORDER BY %s", sortBy));
        }

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        results = dataRepository.getSimpleDataFromCosmos(EXPORT_CONTAINER_NAME, sql);

        return getExportContainerDto(results);
    }

    @Override
    public List<EmptyContainerDto> findEmptyContainer(
            Query query,
            String containerType,
            String facilityId,
            String containerNumber,
            String containerOperationLineId,
            String containerVisitState,
            String containerTransitState,
            String containerEquipmentType,
            String containerIsoGroup,
            String containerArrivePosLocType,
            String containerDepartPosLocType,
            String containerDepartPosLocId,
            String containerArrivePosLocId,
            LocalDateTime arriveFrom,
            LocalDateTime arriveTo,
            LocalDateTime departFrom,
            LocalDateTime departTo,
            String containerUniqueKey,
            String bolNumber,
            String bookingNumber,
            String lastVisitFlag,
            String impedType,
            String operationType,
            List<String> terminalConditions) {

        List<JSONObject> results = new ArrayList<>();

        // Main query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(EMPTY_CONTAINER_BASE_QUERY, filterLastVisitFlag(lastVisitFlag), currentTime));

        // Persona filter
        List<String> personaFilters = buildEmptyContainerConditions(
                facilityId,
                containerVisitState,
                containerTransitState,
                containerIsoGroup,
                containerArrivePosLocType,
                containerDepartPosLocType,
                containerDepartPosLocId,
                containerArrivePosLocId,
                containerNumber,
                containerEquipmentType,
                containerOperationLineId,
                arriveFrom,
                arriveTo,
                departFrom,
                departTo,
                containerUniqueKey,
                bolNumber,
                bookingNumber
        );

        personaFilters = personaFilters.stream()
                .filter(e -> !e.equalsIgnoreCase(""))
                .filter(e -> !e.equalsIgnoreCase("1=1"))
                .collect(Collectors.toList());

        if (personaFilters.size() == 0) {
            queryBuilder.append(" AND ");
        }
        else {
            queryBuilder.append(String.format(" AND %s", "(" + String.join(" " + operationType + " ", personaFilters) + ")"));
            queryBuilder.append(" AND ");
        }

        // Search filter
        QueryBuilder filterBuilder = new QueryBuilder();

        if (query.filter != null) {
            String filter = filterBuilder.buildCosmosSearchFilter(query);
            queryBuilder.append(filter);
        }
        else queryBuilder.append("1=1");

        // Terminal condition
        if(!terminalConditions.contains("ALL")) {
            queryBuilder.append(" AND ");
            List<String> conditions = new ArrayList<>();
            for (String terminalCondition : terminalConditions) {
                conditions.add(String.format("c.facility_id = '%s'", terminalCondition));
            }
            queryBuilder.append("(" + String.join(" OR ", conditions) + ")");
        }

        // Order
        if (!query.sort.isEmpty()) {
            String sortBy = filterBuilder.buildOrderByString(query.sort);
            queryBuilder.append(String.format(" ORDER BY %s", sortBy));
        }

        // Offset limit
        queryBuilder.append(String.format(" OFFSET %s LIMIT %s", query.offset, query.limit));

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        results = dataRepository.getSimpleDataFromCosmos(EMPTY_CONTAINER_NAME, sql);
        return getEmptyContainerDto(results);
    }
}
