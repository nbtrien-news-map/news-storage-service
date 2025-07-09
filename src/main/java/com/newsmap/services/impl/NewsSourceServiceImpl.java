package com.newsmap.services.impl;

import com.newsmap.entities.NewsSourceEntity;
import com.newsmap.repositories.NewsSourceRepository;
import com.newsmap.services.NewsSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsSourceServiceImpl implements NewsSourceService {
    private final NewsSourceRepository newsSourceRepository;
    @Override
    public List<NewsSourceEntity> getAll() {
        return newsSourceRepository.findAll();
    }

    @Override
    @Cacheable(value = "activeNewsSources")
    public List<NewsSourceEntity> getAllActive() {
        return newsSourceRepository.findAllByIsActiveIsTrue();
    }
}
