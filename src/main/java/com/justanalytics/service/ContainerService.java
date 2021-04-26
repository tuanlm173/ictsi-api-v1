package com.justanalytics.service;

import com.justanalytics.entity.Container;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ContainerService {

    List<Container> getTopContainer();

    List<Container> getTopContainerCustom(String containerNumber);

    List<Map<String, Object>> findContainerBol(String containerType, String containerName, String billOfLadingNbr, String size);


}
