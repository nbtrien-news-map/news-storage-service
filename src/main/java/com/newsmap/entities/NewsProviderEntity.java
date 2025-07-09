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
@Table(name = "news_provider")
public class NewsProviderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_provider_id")
    private Integer newsProviderId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "domain", length = 255, nullable = false)
    private String domain;

    @Column(name = "source_type", length = 50)
    private String sourceType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "code")
    private Integer code;
}