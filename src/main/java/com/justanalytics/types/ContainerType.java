package com.justanalytics.types;

public enum ContainerType {

    EXPORT("export"),
    IMPORT("import"),
    SIMPLE("simple");

    private String ContainerType;

    ContainerType(String containerType) {this.ContainerType = containerType;}

    public String getContainerType() {return ContainerType;}


}
