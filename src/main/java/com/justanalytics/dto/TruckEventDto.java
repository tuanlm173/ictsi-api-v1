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
        "event_descriptions", "container_gkey", "applied_to_id", "truck_visit_gkey", "notes", "field_changes", "category", "sub_category", "sequence"})
public class TruckEventDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "operator_id")
    private String operatorId;
    @JsonProperty(value = "complex_id")
    private String complexId;
    @JsonProperty(value = "facility_id")
    private String facilityId;
    @JsonProperty(value = "yard_id")
    private String yardId;
    @JsonProperty(value = "placed_by")
    private String placedBy;
    @JsonProperty(value = "placed_time")
    private String placedTime;
    @JsonProperty(value = "event_type")
    private String eventType;
    @JsonProperty(value = "event_descriptions")
    private List<LanguageDescription> eventDescriptions;
    @JsonProperty(value = "container_gkey")
    private String containerGkey;
    @JsonProperty(value = "applied_to_id")
    private String appliedToId;
    @JsonProperty(value = "truck_visit_gkey")
    private String truckVisitGkey;
    @JsonProperty(value = "notes")
    private String notes;
    @JsonProperty(value = "field_changes")
    private List<FieldChanges> fieldChanges;
    @JsonProperty(value = "category")
    private String category;
    @JsonProperty(value = "sub_category")
    private String subCategory;
    @JsonProperty(value = "sequence")
    private Integer sequence;

}
