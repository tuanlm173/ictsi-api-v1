package com.justanalytics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "azure.cosmos")
public class CosmosDbProperties {

    private String uri;
    private String key;
    private String database;
    private  String getContainerEventCnt;
    private String getContainerDetailsCnt;
    private String getVesselVisitDetailsCnt;
    private String getTruckVisitDetailsCnt;
    private String getFacilityCnt;
    private String getVesselEventCnt;
    private String getTruckEventCnt;
    private String getTruckTransactionsCnt;
    private String getCustomerCnt;
    private boolean populateQueryMetrics;

}
