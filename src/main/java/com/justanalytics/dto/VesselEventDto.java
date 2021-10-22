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
@JsonPropertyOrder({"unique_key", "operator", "complex", "facility", "yard", "placed_by", "placed_time", "event_type",
        "event_descriptions", "notifiable", "vessel_gkey", "applied_to_id", "notes", "field_changes"})
public class VesselEventDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "operator")
    private String operator;
    @JsonProperty(value = "complex")
    private String complex;
    @JsonProperty(value = "facility")
    private String facility;
    @JsonProperty(value = "yard")
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
    @JsonProperty(value = "vessel_gkey")
    private String vesselGkey;
    @JsonProperty(value = "applied_to_id")
    private String appliedToId;
    @JsonProperty(value = "notes")
    private String notes;
    @JsonProperty(value = "field_changes")
    private List<FieldChanges> fieldChanges;

}
