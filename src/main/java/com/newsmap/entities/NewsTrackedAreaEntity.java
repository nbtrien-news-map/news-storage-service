package com.newsmap.entities;

import com.newsmap.entities.converters.BoundingBoxConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news_tracked_area")
public class NewsTrackedAreaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_tracked_area_id")
    private Long newsTrackedAreaId;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @Column(name = "osm_type", nullable = false)
    private String osmType;

    @Column(name = "osm_id", nullable = false)
    private Long osmId;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "admin_level")
    private Short adminLevel;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "osm_class")
    private String osmClass;

    @Column(name = "osm_type_name")
    private String osmTypeName;

    @Column(name = "place_rank")
    private Integer placeRank;

    @Column(name = "importance")
    private Double importance;

    @Column(name = "address_type")
    private String addressType;

    @Convert(converter = BoundingBoxConverter.class)
    @Column(name = "bounding_box", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private List<Double> boundingBox;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "news_source_tracked_area",
            joinColumns = @JoinColumn(name = "news_tracked_area_id"),
            inverseJoinColumns = @JoinColumn(name = "news_source_id"))
    private Set<NewsSourceEntity> newsSources = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "map_news_item_tracked_area",
            joinColumns = @JoinColumn(name = "news_tracked_area_id"),
            inverseJoinColumns = @JoinColumn(name = "map_news_item_id"))
    private Set<MapNewsItemEntity> mapNewsItems = new HashSet<>();
}