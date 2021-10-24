package com.justanalytics.service;

import com.justanalytics.dto.ContainerEventDto;
import com.justanalytics.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContainerEventService {

    List<ContainerEventDto> findContainerEvent(
            String uniqueKey,
            String language,
            String operationType,
            Query query
    );
}
