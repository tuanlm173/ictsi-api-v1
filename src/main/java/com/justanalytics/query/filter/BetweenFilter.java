package com.justanalytics.query.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class BetweenFilter extends SimpleFilter {
    private Object from;
    private Object to;

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }

    @Override
    @JsonIgnore
    public List<Object> getValues() {
        return List.of(from, to);
    }

}