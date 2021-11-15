package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfigDto {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "subscription_id")
    private String subscriptionId;
    @JsonProperty(value = "subscription_name")
    private String subscriptionName;
    @JsonProperty(value = "product_id")
    private String productId;
    @JsonProperty(value = "entity")
    private String entity;
    @JsonProperty(value = "condition")
    private String condition;
}
