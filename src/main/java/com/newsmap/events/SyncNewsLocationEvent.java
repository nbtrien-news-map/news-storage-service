package com.newsmap.events;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SyncNewsLocationEvent {
    private Long mapNewsItemId;
    private String title;
    private String description;
    private String address;
    private GeocodingLocationEvent geocodingLocation;
    private Set<TrackedAreaEvent> trackedAreas = new HashSet<>();
    private Set<Long> trackedAreaIds;
}
