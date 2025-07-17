package com.newsmap.scheduler;

import com.newsmap.services.impl.NewsCrawlDispatcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsCrawlScheduler {
    private final NewsCrawlDispatcherService newsCrawlDispatcherService;

//    @Scheduled(fixedRate = 300000)
    public void execute() {
        newsCrawlDispatcherService.executeService();
    }
}
