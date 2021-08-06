package com.justanalytics.query.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FilterType {
    @JsonProperty(value = "comparison")
    COMPARISON,
    @JsonProperty(value = "between")
    BETWEEN,
    @JsonProperty(value = "in")
    IN,
    @JsonProperty(value = "compound")
    COMPOUND
}
