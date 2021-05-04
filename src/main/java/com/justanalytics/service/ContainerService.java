package com.justanalytics.service;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ContainerService {

    List<Map<String, Object>> findContainerBol(String containerType, String containerName, String billOfLadingNbr, String size);

    List<JSONObject> findContainerBolCosmos(String containerName, String billOfLadingNbr, String size);
}
