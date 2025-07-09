package com.newsmap.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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

    @JoinColumn(name = "geocoding_location_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private GeocodingLocationEntity geocodingLocationEntity;

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