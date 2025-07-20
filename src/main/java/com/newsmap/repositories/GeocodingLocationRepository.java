package com.newsmap.repositories;

import com.newsmap.entities.GeocodingLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeocodingLocationRepository extends JpaRepository<GeocodingLocationEntity, Long> {
    Optional<GeocodingLocationEntity> findByOsmTypeAndOsmId(String osmType, Long osmId);
}
