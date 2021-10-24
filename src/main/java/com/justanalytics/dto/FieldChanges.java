package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldChanges {

    @JsonProperty(value = "metafield_id")
    private String metaFieldId;
    @JsonProperty(value = "data_type")
    private String dataType;
    @JsonProperty(value = "prior_value")
    private String priorValue;
    @JsonProperty(value = "new_value")
    private String newValue;

}
