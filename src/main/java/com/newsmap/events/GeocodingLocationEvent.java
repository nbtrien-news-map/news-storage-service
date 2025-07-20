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
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer placeRank;
    private List<Double> boundingBox;
    private String displayName;
    private String addressType;
    private Double importance;
    private String osmType;
    private Long osmId;
    private String osmClass;
    private String osmTypeName;
}
