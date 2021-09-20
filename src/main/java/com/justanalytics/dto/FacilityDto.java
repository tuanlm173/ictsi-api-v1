package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"unique_key", "terminal_operator_id", "terminal_operator_name", "complex_id", "facility_id", "facility_location",
        "facility_timezone", "terminal_portcd", "local_currency", "region"})
public class FacilityDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "terminal_operator_id")
    private String terminalOperatorId;
    @JsonProperty(value = "terminal_operator_name")
    private String terminalOperatorName;
    @JsonProperty(value = "complex_id")
    private String complexId;
    @JsonProperty(value = "facility_id")
    private String facilityId;
    @JsonProperty(value = "facility_location")
    private String facilityLocation;
    @JsonProperty(value = "facility_timezone")
    private String facilityTimezone;
    @JsonProperty(value = "terminal_portcd")
    private String terminalPortcd;
    @JsonProperty(value = "local_currency")
    private String localCurrency;
    @JsonProperty(value = "region")
    private String region;

}
