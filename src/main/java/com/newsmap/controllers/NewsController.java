//package com.newsmap.controllers;
//
//import com.newsmap.models.NewsCrawlerRequestModel;
//import com.newsmap.services.NewsProcessorService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/news")
//public class NewsController {
//    private final NewsProcessorService cadnNewsProcessorService;
//    private final RequestNewsCrawlerProducer requestNewsCrawlerProducer;
//
//    @GetMapping
//    public ResponseEntity<?> fetchNews() {
//        requestNewsCrawlerProducer.syncStudentMessage(NewsCrawlerRequestModel.builder()
//                                                              .id(1L)
//                                                              .description("des")
//                                                              .build());
//        return ResponseEntity.ok("ok");
//    }
//}
