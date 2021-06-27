package com.justanalytics.service;

import com.justanalytics.dto.ContainerDto;
import com.justanalytics.dto.EmptyContainerDto;
import com.justanalytics.dto.ExportContainerDto;
import com.justanalytics.repository.DataRepository;
import com.justanalytics.types.ContainerImped;
import com.justanalytics.types.ContainerType;
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

    @Autowired
    private DataRepository dataRepository;

    private StringBuilder buildSimpleContainerQuery(String query, String size) {
        StringBuilder queryBuilder = new StringBuilder();
        return queryBuilder.append(String.format(query, size));
    }

    private String buildSimpleContainerParam(String filter, String inputContainerFields) {
        if (inputContainerFields != null && !inputContainerFields.isBlank()) {
            String[] containers = inputContainerFields.split(",");
            List<String> results = new ArrayList<>();
            for (String container: containers) {
                results.add(String.format(filter, container));
            }
            return "(" + String.join(" OR ", results) + ")";
        }
        else return DEFAULT_CONDITION;
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
            String uniqueKey = String.valueOf(data.get("UniqueKey"));
            String operatorId = String.valueOf(data.get("OperatorID"));
            String complexId = String.valueOf(data.get("ComplexID"));
            String facilityId = String.valueOf(data.get("FacilityID"));
            String visitState = String.valueOf(data.get("visit_state"));
            String containerNbr = String.valueOf(data.get("ContainerNbr"));
            String equipmentType = String.valueOf(data.get("EquipmentType"));
            Integer teu = Integer.valueOf(String.valueOf(data.get("TEU")));
            String operatorLineId = String.valueOf(data.get("OperatorLineID"));
            String operatorName = String.valueOf(data.get("OperatorName"));
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
            String bookingNumber = String.valueOf(data.get("BookingNumber"));
            String requiresPower = String.valueOf(data.get("requires_power"));
            String flexString01 = String.valueOf(data.get("flex_string01"));
            String flexString02 = String.valueOf(data.get("flex_string02"));
            String flexString03 = String.valueOf(data.get("flex_string03"));
            String flexString04 = String.valueOf(data.get("flex_string04"));
            String flexString05 = String.valueOf(data.get("flex_string05"));
            String flexString06 = String.valueOf(data.get("flex_string06"));
            String flexString07 = String.valueOf(data.get("flex_string07"));
            String flexString08 = String.valueOf(data.get("flex_string08"));
            String flexString09 = String.valueOf(data.get("flex_string09"));
            String flexString10 = String.valueOf(data.get("flex_string10"));
            String flexString11 = String.valueOf(data.get("flex_string11"));
            String flexString12 = String.valueOf(data.get("flex_string12"));
            String flexString13 = String.valueOf(data.get("flex_string13"));
            String flexString14 = String.valueOf(data.get("flex_string14"));
            String flexString15 = String.valueOf(data.get("flex_string15"));
            String timeStateChange = String.valueOf(data.get("time_state_change"));
            String pod = String.valueOf(data.get("POD"));
            String transitState = String.valueOf(data.get("transit_state"));
            String nominalLength = String.valueOf(data.get("nominal_length"));
            String reeferType = String.valueOf(data.get("reefer_type"));
            String isoGroup = String.valueOf(data.get("iso_group"));
            String masterBlNbr = String.valueOf(data.get("Master_BLNbr"));
            String origin = String.valueOf(data.get("Origin"));
            String destination = String.valueOf(data.get("Destination"));
            String consigneeId = String.valueOf(data.get("Consignee_ID"));
            String consigneeName = String.valueOf(data.get("Consignee_Name"));
            String shipperId = String.valueOf(data.get("Shipper_ID"));
            String shipperName = String.valueOf(data.get("Shipper_Name"));
            String houseBlNbr = String.valueOf(data.get("House_BLNbr"));
            String cargoCategory = String.valueOf(data.get("Cargo_Category"));
            String cargoConsigneeId = String.valueOf(data.get("Cargo_Consignee_ID"));
            String cargoConsigneeName = String.valueOf(data.get("Cargo_Consignee_Name"));
            String cargoShipperId = String.valueOf(data.get("Cargo_Shipper_ID"));
            String cargoShipperName = String.valueOf(data.get("Cargo_Shipper_Name"));
            String cargoOrigin = String.valueOf(data.get("Cargo_Origin"));

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
                    .flexString01(flexString01)
                    .flexString02(flexString02)
                    .flexString03(flexString03)
                    .flexString04(flexString04)
                    .flexString05(flexString05)
                    .flexString06(flexString06)
                    .flexString07(flexString07)
                    .flexString08(flexString08)
                    .flexString09(flexString09)
                    .flexString10(flexString10)
                    .flexString11(flexString11)
                    .flexString12(flexString12)
                    .flexString13(flexString13)
                    .flexString14(flexString14)
                    .flexString15(flexString15)
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
                    .build());

        }

        return results;
    }

    private List<EmptyContainerDto> getEmptyContainerDto(List<JSONObject> rawData) {

        List<EmptyContainerDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("UniqueKey"));
            String operatorId = String.valueOf(data.get("OperatorID"));
            String complexId = String.valueOf(data.get("ComplexID"));
            String facilityId = String.valueOf(data.get("FacilityID"));
            String visitState = String.valueOf(data.get("visit_state"));
            String containerNbr = String.valueOf(data.get("ContainerNbr"));
            String equipmentType = String.valueOf(data.get("EquipmentType"));
            Integer teu = Integer.valueOf(String.valueOf(data.get("TEU")));
            String operatorLineId = String.valueOf(data.get("OperatorLineID"));
            String operatorName = String.valueOf(data.get("OperatorName"));
            String createTime = String.valueOf(data.get("create_time"));
            String category = String.valueOf(data.get("category"));
            String freightKind = String.valueOf(data.get("freight_kind"));
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
            String flexString01 = String.valueOf(data.get("flex_string01"));
            String flexString02 = String.valueOf(data.get("flex_string02"));
            String flexString03 = String.valueOf(data.get("flex_string03"));
            String flexString04 = String.valueOf(data.get("flex_string04"));
            String flexString05 = String.valueOf(data.get("flex_string05"));
            String flexString06 = String.valueOf(data.get("flex_string06"));
            String flexString07 = String.valueOf(data.get("flex_string07"));
            String flexString08 = String.valueOf(data.get("flex_string08"));
            String flexString09 = String.valueOf(data.get("flex_string09"));
            String flexString10 = String.valueOf(data.get("flex_string10"));
            String flexString11 = String.valueOf(data.get("flex_string11"));
            String flexString12 = String.valueOf(data.get("flex_string12"));
            String flexString13 = String.valueOf(data.get("flex_string13"));
            String flexString14 = String.valueOf(data.get("flex_string14"));
            String flexString15 = String.valueOf(data.get("flex_string15"));
            String timeStateChange = String.valueOf(data.get("time_state_change"));
            String transitState = String.valueOf(data.get("transit_state"));
            String nominalLength = String.valueOf(data.get("nominal_length"));
            String reeferType = String.valueOf(data.get("reefer_type"));
            String isoGroup = String.valueOf(data.get("iso_group"));

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
                    .flexString01(flexString01)
                    .flexString02(flexString02)
                    .flexString03(flexString03)
                    .flexString04(flexString04)
                    .flexString05(flexString05)
                    .flexString06(flexString06)
                    .flexString07(flexString07)
                    .flexString08(flexString08)
                    .flexString09(flexString09)
                    .flexString10(flexString10)
                    .flexString11(flexString11)
                    .flexString12(flexString12)
                    .flexString13(flexString13)
                    .flexString14(flexString14)
                    .flexString15(flexString15)
                    .timeStateChange(timeStateChange)
                    .transitState(transitState)
                    .nominalLength(nominalLength)
                    .reeferType(reeferType)
                    .isoGroup(isoGroup)
                    .build());

        }

        return results;
    }

    private List<ExportContainerDto> getExportContainerDto(List<JSONObject> rawData) {

        List<ExportContainerDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("UniqueKey"));
            String operatorId = String.valueOf(data.get("OperatorID"));
            String complexId = String.valueOf(data.get("ComplexID"));
            String facilityId = String.valueOf(data.get("FacilityID"));
            String visitState = String.valueOf(data.get("visit_state"));
            String containerNbr = String.valueOf(data.get("ContainerNbr"));
            String equipmentType = String.valueOf(data.get("EquipmentType"));
            Integer teu = Integer.valueOf(String.valueOf(data.get("TEU")));
            String operatorLineId = String.valueOf(data.get("OperatorLineID"));
            String operatorName = String.valueOf(data.get("OperatorName"));
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
            String bookingNumber = String.valueOf(data.get("BookingNumber"));
            String requiresPower = String.valueOf(data.get("requires_power"));
            String flexString01 = String.valueOf(data.get("flex_string01"));
            String flexString02 = String.valueOf(data.get("flex_string02"));
            String flexString03 = String.valueOf(data.get("flex_string03"));
            String flexString04 = String.valueOf(data.get("flex_string04"));
            String flexString05 = String.valueOf(data.get("flex_string05"));
            String flexString06 = String.valueOf(data.get("flex_string06"));
            String flexString07 = String.valueOf(data.get("flex_string07"));
            String flexString08 = String.valueOf(data.get("flex_string08"));
            String flexString09 = String.valueOf(data.get("flex_string09"));
            String flexString10 = String.valueOf(data.get("flex_string10"));
            String flexString11 = String.valueOf(data.get("flex_string11"));
            String flexString12 = String.valueOf(data.get("flex_string12"));
            String flexString13 = String.valueOf(data.get("flex_string13"));
            String flexString14 = String.valueOf(data.get("flex_string14"));
            String flexString15 = String.valueOf(data.get("flex_string15"));
            String timeStateChange = String.valueOf(data.get("time_state_change"));
            String pod = String.valueOf(data.get("POD"));
            String transitState = String.valueOf(data.get("transit_state"));
            String nominalLength = String.valueOf(data.get("nominal_length"));
            String reeferType = String.valueOf(data.get("reefer_type"));
            String isoGroup = String.valueOf(data.get("iso_group"));

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
                    .flexString01(flexString01)
                    .flexString02(flexString02)
                    .flexString03(flexString03)
                    .flexString04(flexString04)
                    .flexString05(flexString05)
                    .flexString06(flexString06)
                    .flexString07(flexString07)
                    .flexString08(flexString08)
                    .flexString09(flexString09)
                    .flexString10(flexString10)
                    .flexString11(flexString11)
                    .flexString12(flexString12)
                    .flexString13(flexString13)
                    .flexString14(flexString14)
                    .flexString15(flexString15)
                    .timeStateChange(timeStateChange)
                    .pod(pod)
                    .transitState(transitState)
                    .nominalLength(nominalLength)
                    .reeferType(reeferType)
                    .isoGroup(isoGroup)
                    .build());

        }

        return results;
    }

    private List<String> buildEmptyContainerConditions(
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
            LocalDateTime departTo
    ) {
        List<String> filters = new ArrayList<>();

        String containerVisitStateFilter = buildSimpleContainerParam(EMPTY_CONTAINER_VISIT_STATE, containerVisitState);
        String containerTransitStateFilter = buildSimpleContainerParam(EMPTY_CONTAINER_TRANSIT_STATE, containerTransitState);
        String containerIsoGroupFilter = buildSimpleContainerParam(EMPTY_CONTAINER_ISO_GROUP, containerIsoGroup);
        String containerArrivePosLocTypeFilter = buildSimpleContainerParam(EMPTY_CONTAINER_ARRIVE_POS_LOCTYPE, containerArrivePosLocType);
        String containerDepartPosLocTypeFilter = buildSimpleContainerParam(EMPTY_CONTAINER_DEPART_POST_LOCTYPE, containerDepartPosLocType);
        String containerDepartPosLocIdFilter = buildSimpleContainerParam(EMPTY_CONTAINER_DEPART_POST_LOC_ID, containerDepartPosLocId);
        String containerArrivePosLocIdFilter = buildSimpleContainerParam(EMPTY_CONTAINER_ARRIVE_POS_LOC_ID, containerArrivePosLocId);
        String containerNumberFilter = buildSimpleContainerParam(EMPTY_CONTAINER_NUMBER, containerNumber);
        String containerEquipmentTypeFilter = buildSimpleContainerParam(EMPTY_CONTAINER_EQUIPMENT_TYPE, containerEquipmentType);
        String containerOperationLineIFilter = buildSimpleContainerParam(EMPTY_CONTAINER_OPERATION_LINE_ID, containerOperationLineId);

        String containerTimeInFilter = buildSimpleTimeframeContainerParam(EMPTY_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String containerTimeOutFilter = buildSimpleTimeframeContainerParam(EMPTY_CONTAINER_TIME_OUT, departFrom, departTo);

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

        return filters;
    }

    private List<String> buildAllContainerConditions(
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
            LocalDateTime departTo
    ) {
        List<String> filters = new ArrayList<>();

        String containerVisitStateFilter = buildSimpleContainerParam(ALL_CONTAINER_VISIT_STATE, containerVisitState);
        String containerTransitStateFilter = buildSimpleContainerParam(ALL_CONTAINER_TRANSIT_STATE, containerTransitState);
        String containerIsoGroupFilter = buildSimpleContainerParam(ALL_CONTAINER_ISO_GROUP, containerIsoGroup);
        String containerArrivePosLocTypeFilter = buildSimpleContainerParam(ALL_CONTAINER_ARRIVE_POS_LOCTYPE, containerArrivePosLocType);
        String containerDepartPosLocTypeFilter = buildSimpleContainerParam(ALL_CONTAINER_DEPART_POST_LOCTYPE, containerDepartPosLocType);
        String containerDepartPosLocIdFilter = buildSimpleContainerParam(ALL_CONTAINER_DEPART_POST_LOC_ID, containerDepartPosLocId);
        String containerArrivePosLocIdFilter = buildSimpleContainerParam(ALL_CONTAINER_ARRIVE_POS_LOC_ID, containerArrivePosLocId);
        String containerNumberFilter = buildSimpleContainerParam(ALL_CONTAINER_NUMBER, containerNumber);
        String containerEquipmentTypeFilter = buildSimpleContainerParam(ALL_CONTAINER_EQUIPMENT_TYPE, containerEquipmentType);
        String containerOperationLineIFilter = buildSimpleContainerParam(ALL_CONTAINER_OPERATION_LINE_ID, containerOperationLineId);

        String containerTimeInFilter = buildSimpleTimeframeContainerParam(ALL_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String containerTimeOutFilter = buildSimpleTimeframeContainerParam(ALL_CONTAINER_TIME_OUT, departFrom, departTo);

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

        return filters;
    }

    private List<String> buildExportContainerConditions(
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
            String impedType
    ) {
        List<String> filters = new ArrayList<>();

        String containerFreightKindFilter = buildSimpleContainerParam(EXPORT_CONTAINER_FREIGHT_KIND, containerFreightKind);
        String containerVisitStateFilter = buildSimpleContainerParam(EXPORT_CONTAINER_VISIT_STATE, containerVisitState);
        String containerTransitStateFilter = buildSimpleContainerParam(EXPORT_CONTAINER_TRANSIT_STATE, containerTransitState);
        String containerIsoGroupFilter = buildSimpleContainerParam(EXPORT_CONTAINER_ISO_GROUP, containerIsoGroup);
        String containerArrivePosLocTypeFilter = buildSimpleContainerParam(EXPORT_CONTAINER_ARRIVE_POS_LOCTYPE, containerArrivePosLocType);
        String containerDepartPosLocTypeFilter = buildSimpleContainerParam(EXPORT_CONTAINER_DEPART_POST_LOCTYPE, containerDepartPosLocType);
        String containerDepartPosLocIdFilter = buildSimpleContainerParam(EXPORT_CONTAINER_DEPART_POST_LOC_ID, containerDepartPosLocId);
        String containerArrivePosLocIdFilter = buildSimpleContainerParam(EXPORT_CONTAINER_ARRIVE_POS_LOC_ID, containerArrivePosLocId);
        String containerNumberFilter = buildSimpleContainerParam(EXPORT_CONTAINER_NUMBER, containerNumber);
        String containerEquipmentTypeFilter = buildSimpleContainerParam(EXPORT_CONTAINER_EQUIPMENT_TYPE, containerEquipmentType);
        String containerOperationLineIdFilter = buildSimpleContainerParam(EXPORT_CONTAINER_OPERATION_LINE_ID, containerOperationLineId);
        String timeInFilter = buildSimpleTimeframeContainerParam(EXPORT_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String timeOutFilter = buildSimpleTimeframeContainerParam(EXPORT_CONTAINER_TIME_OUT, departFrom, departTo);
        String containerBookingNumberFilter = buildSimpleContainerParam(EXPORT_CONTAINER_BOOKING_NUMBER, containerBookingNumber);

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

        return filters;
    }

    private List<String> buildImportContainerConditions(
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
            String impedType
    ) {
        List<String> filters = new ArrayList<>();

        String containerFreightKindFilter = buildSimpleContainerParam(IMPORT_CONTAINER_FREIGHT_KIND, containerFreightKind);
        String containerVisitStateFilter = buildSimpleContainerParam(IMPORT_CONTAINER_VISIT_STATE, containerVisitState);
        String containerTransitStateFilter = buildSimpleContainerParam(IMPORT_CONTAINER_TRANSIT_STATE, containerTransitState);
        String containerIsoGroupFilter = buildSimpleContainerParam(IMPORT_CONTAINER_ISO_GROUP, containerIsoGroup);
        String containerArrivePosLocTypeFilter = buildSimpleContainerParam(IMPORT_CONTAINER_ARRIVE_POS_LOCTYPE, containerArrivePosLocType);
        String containerDepartPosLocTypeFilter = buildSimpleContainerParam(IMPORT_CONTAINER_DEPART_POST_LOCTYPE, containerDepartPosLocType);
        String containerDepartPosLocIdFilter = buildSimpleContainerParam(IMPORT_CONTAINER_DEPART_POST_LOC_ID, containerDepartPosLocId);
        String containerArrivePosLocIdFilter = buildSimpleContainerParam(IMPORT_CONTAINER_ARRIVE_POS_LOC_ID, containerArrivePosLocId);
        String containerNumberFilter = buildSimpleContainerParam(IMPORT_CONTAINER_NUMBER, containerNumber);
        String containerEquipmentTypeFilter = buildSimpleContainerParam(IMPORT_CONTAINER_EQUIPMENT_TYPE, containerEquipmentType);
        String containerOperationLineIdFilter = buildSimpleContainerParam(IMPORT_CONTAINER_OPERATION_LINE_ID, containerOperationLineId);
        String timeInFilter = buildSimpleTimeframeContainerParam(IMPORT_CONTAINER_TIME_IN, arriveFrom, arriveTo);
        String timeOutFilter = buildSimpleTimeframeContainerParam(IMPORT_CONTAINER_TIME_OUT, departFrom, departTo);
        String containerBookingNumberFilter = buildSimpleContainerParam(IMPORT_CONTAINER_BOOKING_NUMBER, containerBookingNumber);
        String containerBolNumberFilter = buildBolNbrParam(IMPORT_CONTAINER_BOL_NUMBER, containerBolNumber);

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

        return filters;
    }

    @Override
    public List<ContainerDto> findContainer(
            String containerType,
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
            String impedType,
            String size,
            String terminalCondition
    ) {
        List<String> filters = new ArrayList<>();
        List<JSONObject> results = new ArrayList<>();

        if (ContainerType.IMPORT.getContainerType().equalsIgnoreCase(containerType)) {
            StringBuilder queryBuilder = buildSimpleContainerQuery(IMPORT_CONTAINER_BASE_QUERY, size);
            filters = buildImportContainerConditions(
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
                    impedType
            );

            filters = filters.stream().filter(e -> !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

            if (filters.size() == 0) {
                queryBuilder.append("");
            } else {
                queryBuilder.append(String.format(" AND %s", String.join(" AND ", filters)));
            }

            if (!terminalCondition.isEmpty() || !terminalCondition.isBlank())
                queryBuilder.append(String.format(" AND c.FacilityID = '%s'", terminalCondition));

            if (size.equalsIgnoreCase("1")) {
                queryBuilder.append(" ORDER BY c.changed DESC");
            }
            String sql = queryBuilder.toString();
            logger.info("Cosmos SQL statement: {}", sql);
            results = dataRepository.getSimpleDataFromCosmos(IMPORT_CONTAINER_NAME, sql);
        }

        else if (ContainerType.ALL.getContainerType().equalsIgnoreCase(containerType)) {
            StringBuilder queryBuilder = buildSimpleContainerQuery(ALL_CONTAINER_BASE_QUERY, size);
            filters = buildAllContainerConditions(
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
                    departTo
            );

            filters = filters.stream().filter(e -> !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

            if (filters.size() == 0) {
                queryBuilder.append("");
            } else {
                queryBuilder.append(String.format(" AND %s", String.join(" AND ", filters)));
            }

            if (!terminalCondition.isEmpty() || !terminalCondition.isBlank())
                queryBuilder.append(String.format(" AND c.FacilityID = '%s'", terminalCondition));

            if (size.equalsIgnoreCase("1")) {
                queryBuilder.append(" ORDER BY c.changed DESC");
            }
            String sql = queryBuilder.toString();
            logger.info("Cosmos SQL statement: {}", sql);
            results = dataRepository.getSimpleDataFromCosmos(ALL_CONTAINER_NAME, sql);

        }

        return getContainerDto(results);

    }

    @Override
    public List<EmptyContainerDto> findEmptyContainer(
            String containerType,
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
            String impedType,
            String size,
            String terminalCondition
    ) {
        List<String> filters;
        List<JSONObject> results;

        StringBuilder queryBuilder = buildSimpleContainerQuery(EMPTY_CONTAINER_BASE_QUERY, size);
        filters = buildEmptyContainerConditions(
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
                departTo
        );

        filters = filters.stream().filter(e -> !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

        if (filters.size() == 0) {
            queryBuilder.append("");
        } else {
            queryBuilder.append(String.format(" AND %s", String.join(" AND ", filters)));
        }

        if (!terminalCondition.isEmpty() || !terminalCondition.isBlank())
            queryBuilder.append(String.format(" AND c.FacilityID = '%s'", terminalCondition));

        if (size.equalsIgnoreCase("1")) {
            queryBuilder.append(" ORDER BY c.changed DESC");
        }
        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        results = dataRepository.getSimpleDataFromCosmos(EMPTY_CONTAINER_NAME, sql);

        return getEmptyContainerDto(results);

    }

    @Override
    public List<ExportContainerDto> findExportContainer(
            String containerType,
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
            String impedType,
            String size,
            String terminalCondition
    ) {
        List<String> filters;
        List<JSONObject> results;

        StringBuilder queryBuilder = buildSimpleContainerQuery(EXPORT_CONTAINER_BASE_QUERY, size);
        filters = buildExportContainerConditions(
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
                impedType

        );

        filters = filters.stream().filter(e -> !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

        if (filters.size() == 0) {
            queryBuilder.append("");
        } else {
            queryBuilder.append(String.format(" AND %s", String.join(" AND ", filters)));
        }

        //TODO: implement for empty, import, all as well
        if (!terminalCondition.isEmpty() || !terminalCondition.isBlank())
            queryBuilder.append(String.format(" AND c.FacilityID = '%s'", terminalCondition));

        if (size.equalsIgnoreCase("1")) {
            queryBuilder.append(" ORDER BY c.changed DESC");
        }
        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        results = dataRepository.getSimpleDataFromCosmos(EXPORT_CONTAINER_NAME, sql);

        return getExportContainerDto(results);

    }


}
