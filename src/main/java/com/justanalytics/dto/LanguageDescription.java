package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDescription {

    @JsonProperty(value = "language")
    private String language;
    @JsonProperty(value = "description")
    private String description;
}
