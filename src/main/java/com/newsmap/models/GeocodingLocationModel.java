package com.newsmap.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GeocodingLocationModel {
    private Long placeId;
    private String name;
    private String latitude;
    private String longitude;
    private int placeRank;
    private double[] boundingBox;
}
