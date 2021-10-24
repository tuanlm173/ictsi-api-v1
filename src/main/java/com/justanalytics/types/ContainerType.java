package com.justanalytics.types;

public enum ContainerType {

    EXPORT("export"),
    IMPORT("import"),
    EMPTY("empty"),
    ALL("all");

    private String ContainerType;

    ContainerType(String containerType) {this.ContainerType = containerType;}

    public String getContainerType() {return ContainerType;}


}
