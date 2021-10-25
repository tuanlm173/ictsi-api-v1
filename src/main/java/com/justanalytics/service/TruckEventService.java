package com.justanalytics.service;


import com.justanalytics.dto.TruckEventDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TruckEventService {

    List<TruckEventDto> findTruckEvent(
            String uniqueKey,
            String language,
            String operationType,
            Query query
    );
}
