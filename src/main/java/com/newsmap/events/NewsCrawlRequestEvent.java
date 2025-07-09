package com.newsmap.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsCrawlRequestEvent {
    private Integer newsSourceId;
    private String sourceUrl;
    private Integer providerCode;
    private String providerName;
    private String sourceType;
}
