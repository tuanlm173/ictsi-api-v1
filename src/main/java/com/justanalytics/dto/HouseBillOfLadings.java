package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseBillOfLadings {

    @JsonProperty(value = "house_bl_nbr")
    private String houseBlNbr;
    @JsonProperty(value = "cargo_consignee_name")
    private String cargoConsigneeName;
    @JsonProperty(value = "cargo_shipper_name")
    private String cargoShipperName;
    @JsonProperty(value = "cargo_origin")
    private String cargoOrigin;
}
