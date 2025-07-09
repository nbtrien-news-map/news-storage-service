package com.newsmap.services;

import com.newsmap.dtos.NewsResponseDto;

import java.util.List;

public interface NewsProcessorService {
    List<NewsResponseDto> fetchNews();
}
