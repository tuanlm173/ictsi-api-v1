package com.justanalytics.service;

import com.justanalytics.dto.ContainerDto;
import com.justanalytics.dto.TruckVisitDto;
import com.justanalytics.repository.DataRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.justanalytics.constant.TruckVisitBaseCondition.*;

@Service
public class TruckVisitServiceImpl implements TruckVisitService {

    Logger logger = LoggerFactory.getLogger(TruckVisitServiceImpl.class);

    private static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private DataRepository dataRepository;

    private StringBuilder buildSimpleTruckVisitQuery(String query, String size) {
        StringBuilder queryBuilder = new StringBuilder();
        return queryBuilder.append(String.format(query, size));
    }

    private String buildSimpleTruckParam(String filter, String inputTruckVisitFields) {
        if (inputTruckVisitFields != null && !inputTruckVisitFields.isBlank()) {
            String[] truckVisits = inputTruckVisitFields.split(",");
            List<String> results = new ArrayList<>();
            for (String truckVisit: truckVisits) {
                results.add(String.format(filter, truckVisit));
            }
            return "(" + String.join(" OR ", results) + ")";
        }
        else return DEFAULT_CONDITION;
    }

    private String buildSimpleTimeframeTruckParam(String filter, LocalDateTime from, LocalDateTime to) {
        if ((from != null && !from.toString().isBlank()) && (to != null && !to.toString().isBlank())) {
            return String.format(filter, from.format(iso_formatter) + 'Z', to.format(iso_formatter) + 'Z');
        }
        return DEFAULT_CONDITION;
    }


    private List<TruckVisitDto> getTruckVisitDto(List<JSONObject> rawData) {

        List<TruckVisitDto> results = new ArrayList<>(rawData.size());

        for (JSONObject data: rawData) {
            String uniqueKey = String.valueOf(data.get("UniqueKey"));
            String facilityId = String.valueOf(data.get("Facility_ID"));
            String truckId = String.valueOf(data.get("TruckID"));
            String visitNbr = String.valueOf(data.get("Visit_Nbr"));
            String visitPhase = String.valueOf(data.get("Visit_Phase"));
            String carrierOperatorId = String.valueOf(data.get("Carrier_Operator_ID"));
            String carrierOperatorName = String.valueOf(data.get("Carrier_Operator_Name"));
            String ata = String.valueOf(data.get("ATA"));
            String atd = String.valueOf(data.get("ATD"));
            String driverLicenseNbr = String.valueOf(data.get("Driver_License_Nbr"));
            String truckLicenseNbr = String.valueOf(data.get("Truck_License_Nbr"));
            String enteredYard = String.valueOf(data.get("Entered_Yard"));
            String exitedYard = String.valueOf(data.get("Exited_Yard"));
            String placedTime = String.valueOf(data.get("PlacedTime"));
            String toLocation = String.valueOf(data.get("ToLocation"));
            String moveKind = String.valueOf(data.get("MoveKind"));
            String fromLocation = String.valueOf(data.get("FromLocation"));
            String category = String.valueOf(data.get("category"));
            String freightKind = String.valueOf(data.get("freight_kind"));
            String placedBy = String.valueOf(data.get("Placed_By"));
            String eventType = String.valueOf(data.get("Event_Type"));
            String appliedToId = String.valueOf(data.get("Applied_To_ID"));

            results.add(TruckVisitDto.builder()
                    .uniqueKey(uniqueKey)
                    .facilityId(facilityId)
                    .truckId(truckId)
                    .visitNbr(visitNbr)
                    .visitPhase(visitPhase)
                    .carrierOperatorId(carrierOperatorId)
                    .carrierOperatorName(carrierOperatorName)
                    .ata(ata)
                    .atd(atd)
                    .driverLicenseNbr(driverLicenseNbr)
                    .truckLicenseNbr(truckLicenseNbr)
                    .enteredYard(enteredYard)
                    .exitedYard(exitedYard)
                    .placedTime(placedTime)
                    .toLocation(toLocation)
                    .moveKind(moveKind)
                    .fromLocation(fromLocation)
                    .category(category)
                    .freightKind(freightKind)
                    .placedBy(placedBy)
                    .eventType(eventType)
                    .appliedToId(appliedToId)
                    .build());

        }
        return results;
    }

    @Override
    public List<TruckVisitDto> findTruckVisit(
            String truckLicenseNbr,
            String moveKind,
            LocalDateTime visitTimeFrom,
            LocalDateTime visitTimeTo,
            String size
    ) {

        List<String> filters = new ArrayList<>();

        StringBuilder queryBuilder = buildSimpleTruckVisitQuery(TRUCK_VISIT_BASE_QUERY, size);

        String truckLicenseNbrFilter = buildSimpleTruckParam(TRUCK_LICENSE_NBR, truckLicenseNbr);
        String truckMoveKindFilter = buildSimpleTruckParam(MOVE_KIND, moveKind);
        String truckVisitTimeFilter = buildSimpleTimeframeTruckParam(VISIT_TIME, visitTimeFrom, visitTimeTo);

        filters.add(truckLicenseNbrFilter);
        filters.add(truckMoveKindFilter);
        filters.add(truckVisitTimeFilter);

        filters = filters.stream().filter(e -> !Objects.equals(e, DEFAULT_CONDITION)).collect(Collectors.toList());

        if (filters.size() == 0) {
            queryBuilder.append("");
        } else {
            queryBuilder.append(String.format(" AND %s", String.join(" AND ", filters)));

        }

        String sql = queryBuilder.toString();
        logger.info("Cosmos SQL statement: {}", sql);
        List<JSONObject> rawData = dataRepository.getSimpleDataFromCosmos(CONTAINER_NAME, sql);
        return getTruckVisitDto(rawData);
    }
}
