package com.justanalytics.types;

public enum ContainerImped {

    LOADING("loading"),
    TRUCK("truck"),
    RAIL("rail"),
    VESSEL("vessel"),
    NONE("none"),
    ANY("any");


    private String ContainerImped;

    ContainerImped(String containerImped) {this.ContainerImped = containerImped;}

    public String getContainerImped() {return ContainerImped;}

}
