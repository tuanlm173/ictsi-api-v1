package com.justanalytics.query.filter;

import java.util.List;

public class INFilter extends SimpleFilter {
    private List<Object> values;

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
