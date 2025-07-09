package com.newsmap.services.impl;

import com.newsmap.entities.NewsTrackedAreaEntity;
import com.newsmap.repositories.NewsTrackedAreaRepository;
import com.newsmap.services.NewsTrackedAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsTrackedAreaServiceImpl implements NewsTrackedAreaService {
    private final NewsTrackedAreaRepository newsTrackedAreaRepository;
    @Override
    @Cacheable(value = "newsTrackedAreas")
    public List<NewsTrackedAreaEntity> getAll() {
        return newsTrackedAreaRepository.findAll();
    }
}
