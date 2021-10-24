package com.justanalytics.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SortOrder {
    @JsonProperty(value = "ASC")
    Ascending("ASC"),
    @JsonProperty(value = "DESC")
    Descending("DESC");

    public String value;

    private SortOrder(String value) {
        this.value = value;
    }
}
