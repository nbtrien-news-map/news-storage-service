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
@Table(name = "news_source")
public class NewsSourceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_source_id")
    private Integer newsSourceId;

    @ManyToOne
    @JoinColumn(name = "news_provider_id", referencedColumnName = "news_provider_id")
    private NewsProviderEntity newsProvider;

    @Column(name = "source_url", length = 500, nullable = false)
    private String sourceUrl;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "news_source_category",
            joinColumns = @JoinColumn(name = "news_source_id"),
            inverseJoinColumns = @JoinColumn(name = "news_category_id"))
    private Set<NewsCategoryEntity> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "news_source_tracked_area",
            joinColumns = @JoinColumn(name = "news_source_id"),
            inverseJoinColumns = @JoinColumn(name = "news_tracked_area_id"))
    private Set<NewsTrackedAreaEntity> trackedAreas = new HashSet<>();
}