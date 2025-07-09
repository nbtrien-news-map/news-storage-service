package com.newsmap.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "map_news_item")
public class MapNewsItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_news_item_id")
    private Long mapNewsItemId;

    @ManyToOne
    @JoinColumn(name = "news_source_id", referencedColumnName = "news_source_id")
    private NewsSourceEntity newsSource;

    @Column(name = "provider", length = 100, nullable = false)
    private String provider;

    @ManyToOne
    @JoinColumn(name = "geocoding_location_id", referencedColumnName = "geocoding_location_id")
    private GeocodingLocationEntity geocodingLocation;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "source_url", length = 500, nullable = false)
    private String sourceUrl;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

//    @Column(name = "metadata", columnDefinition = "jsonb")
//    private String metadata;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "map_news_item_category",
            joinColumns = @JoinColumn(name = "map_news_item_id"),
            inverseJoinColumns = @JoinColumn(name = "news_category_id"))
    private Set<NewsCategoryEntity> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "map_news_item_tracked_area",
            joinColumns = @JoinColumn(name = "map_news_item_id"),
            inverseJoinColumns = @JoinColumn(name = "news_tracked_area_id"))
    private Set<NewsTrackedAreaEntity> trackedAreas = new HashSet<>();
}