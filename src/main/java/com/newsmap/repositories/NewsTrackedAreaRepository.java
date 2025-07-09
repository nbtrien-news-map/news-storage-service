package com.newsmap.repositories;

import com.newsmap.entities.NewsTrackedAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTrackedAreaRepository extends JpaRepository<NewsTrackedAreaEntity, Long> {
}
