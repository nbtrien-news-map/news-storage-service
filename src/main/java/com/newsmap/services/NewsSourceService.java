package com.newsmap.services;

import com.newsmap.entities.NewsSourceEntity;

import java.util.List;

public interface NewsSourceService {
    List<NewsSourceEntity> getAll();
    List<NewsSourceEntity> getAllActive();
}
