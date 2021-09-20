package com.justanalytics.query.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ComparisonOperator {
    @JsonProperty(value = "EQ")
    EQ("="),
    @JsonProperty(value = "NE")
    NE("!="),
    @JsonProperty(value = "LT")
    LT("<"),
    @JsonProperty(value = "LE")
    LE("<="),
    @JsonProperty(value = "GT")
    GT(">"),
    @JsonProperty(value = "GE")
    GE(">="),
    @JsonProperty(value = "LIKE")
    LIKE("LIKE");

    public String value;

    ComparisonOperator(String value) {
        this.value = value;
    }

}
