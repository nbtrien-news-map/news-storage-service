package com.newsmap.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geocoding_location")
public class GeocodingLocationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "geocoding_location_id")
    private Long geocodingLocationId;

    @Column(name = "place_id", unique = true)
    private Long placeId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "place_rank")
    private Integer placeRank;

    @Column(name = "importance")
    private Double importance;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "bounding_box", columnDefinition = "jsonb")
    private String boundingBox;
}