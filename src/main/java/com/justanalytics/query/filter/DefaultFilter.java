package com.justanalytics.query.filter;

public enum DefaultFilter {


    DEFAULT_TRUE("1=1"),
    DEFAULT_FALSE("1=0");

    private String DefaultFilter;

    DefaultFilter(String defaultFilter) {this.DefaultFilter = DefaultFilter;}

    public String getDefaultFilter() {return DefaultFilter;}


}
