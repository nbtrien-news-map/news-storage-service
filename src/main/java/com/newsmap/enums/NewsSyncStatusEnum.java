package com.newsmap.enums;

import lombok.Getter;

@Getter
public enum NewsSyncStatusEnum {
    SYNCED_RAW_DATA((short) 1),
    SYNCED_GEOCODING_LOCATION((short) 2),
    SYNC_GEOCODING_LOCATION_FAILED((short) 3);

    private final short value;

    NewsSyncStatusEnum(short value) {
        this.value = value;
    }

    public static NewsSyncStatusEnum fromValue(Short value) {
        for (NewsSyncStatusEnum status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
