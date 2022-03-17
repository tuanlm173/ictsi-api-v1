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
@JsonPropertyOrder({"unique_key", "operator_id", "complex_id", "facility_id", "yard_id", "placed_by", "placed_time", "event_type",
        "event_descriptions", "notifiable", "container_gkey", "applied_to_id", "notes", "field_changes", "category", "sub_category"})
public class ContainerEventDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "operator_id")
    private String operator;
    @JsonProperty(value = "complex_id")
    private String complex;
    @JsonProperty(value = "facility_id")
    private String facility;
    @JsonProperty(value = "yard_id")
    private String yard;
    @JsonProperty(value = "placed_by")
    private String placedBy;
    @JsonProperty(value = "placed_time")
    private String placedTime;
    @JsonProperty(value = "event_type")
    private String eventType;
    @JsonProperty(value = "event_descriptions")
    private List<LanguageDescription> eventDescriptions;
    @JsonProperty(value = "notifiable")
    private Boolean notifiable;
    @JsonProperty(value = "container_gkey")
    private String containerGkey;
    @JsonProperty(value = "applied_to_id")
    private String appliedToId;
    @JsonProperty(value = "notes")
    private String notes;
    @JsonProperty(value = "field_changes")
    private List<FieldChanges> fieldChanges;
    @JsonProperty(value = "category")
    private String category;
    @JsonProperty(value = "sub_category")
    private String subCategory;

}
