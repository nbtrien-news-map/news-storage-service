package com.newsmap.repositories;

import com.newsmap.entities.MapNewsItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapNewsItemRepository extends JpaRepository<MapNewsItemEntity, Long> {
    boolean existsBySourceUrl(String sourceUrl);

    Optional<MapNewsItemEntity> findByMapNewsItemIdAndSyncStatus(Long id, Short syncStatus);
}
