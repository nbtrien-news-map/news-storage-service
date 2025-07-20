package com.newsmap.enums;

import lombok.Getter;

@Getter
public enum OsmTypeNum {
    NODE("node"),
    WAY("way"),
    RELATION("relation");

    private final String value;

    OsmTypeNum(String value) {
        this.value = value;
    }

    public static OsmTypeNum fromValue(String value) {
        for (OsmTypeNum type : OsmTypeNum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OSM type: " + value);
    }
}
