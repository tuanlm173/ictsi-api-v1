package com.justanalytics.service;

import com.justanalytics.dto.FacilityDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface FacilityService {

    List<FacilityDto> findFacility(
            Query query
    );
}
