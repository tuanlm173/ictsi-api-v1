package com.justanalytics.service;

import com.justanalytics.dto.TruckVisitDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TruckVisitService {

    List<TruckVisitDto> findTruckVisit(
        String truckLicenseNbr,
        String moveKind,
        LocalDateTime visitTimeFrom,
        LocalDateTime visitTimeTo,
        String size
    );
}
