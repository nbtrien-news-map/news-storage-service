package com.newsmap.repositories;

import com.newsmap.entities.MapNewsItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapNewsItemRepository extends JpaRepository<MapNewsItemEntity, Long> {
    boolean existsBySourceUrl(String sourceUrl);
}
