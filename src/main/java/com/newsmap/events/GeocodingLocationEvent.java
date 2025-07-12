package com.newsmap.events;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeocodingLocationEvent {
    private Long placeId;
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer placeRank;
    private List<Double> boundingBox;
    private String displayName;
    private String addressType;
    private Double importance;
}
