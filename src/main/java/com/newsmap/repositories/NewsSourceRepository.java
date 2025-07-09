package com.newsmap.repositories;

import com.newsmap.entities.NewsSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsSourceRepository extends JpaRepository<NewsSourceEntity, Integer> {
    List<NewsSourceEntity> findAllByIsActiveIsTrue();
}
