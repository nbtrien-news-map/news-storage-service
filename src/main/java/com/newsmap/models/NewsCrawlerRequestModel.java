package com.newsmap.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class NewsCrawlerRequestModel {
    private Long id;
    private String description;
}
