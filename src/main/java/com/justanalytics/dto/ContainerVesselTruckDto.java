package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"container", "vessel", "truck"})
public class ContainerVesselTruckDto {

    @JsonProperty(value = "container")
    private List<ContainerDto> containerDto;
    @JsonProperty(value = "vessel")
    private List<VesselVisitDto> vesselVisitDto;
    @JsonProperty(value = "truck")
    private List<TruckVisitDto> truckVisitDto;
}
