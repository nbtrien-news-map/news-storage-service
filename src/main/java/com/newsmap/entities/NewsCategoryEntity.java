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
@Table(name = "news_category")
public class NewsCategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_category_id")
    private Integer newsCategoryId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}