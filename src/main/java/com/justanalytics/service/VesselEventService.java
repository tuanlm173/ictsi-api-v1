package com.justanalytics.service;

import com.justanalytics.dto.VesselEventDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VesselEventService {

    List<VesselEventDto> findVesselEvent(
            String uniqueKey,
            String language,
            String operationType,
            Query query
    );
}
