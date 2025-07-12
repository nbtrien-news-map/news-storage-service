package com.newsmap.entities;

import com.newsmap.entities.converters.BoundingBoxConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Convert(converter = BoundingBoxConverter.class)
    @Column(name = "bounding_box", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private List<Double> boundingBox;
}