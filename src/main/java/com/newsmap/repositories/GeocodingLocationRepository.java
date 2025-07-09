package com.newsmap.repositories;

import com.newsmap.entities.GeocodingLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeocodingLocationRepository extends JpaRepository<GeocodingLocationEntity, Long> {
}
