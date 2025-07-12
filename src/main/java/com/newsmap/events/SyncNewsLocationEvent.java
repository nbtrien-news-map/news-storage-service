package com.newsmap.events;

import lombok.*;

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
}
