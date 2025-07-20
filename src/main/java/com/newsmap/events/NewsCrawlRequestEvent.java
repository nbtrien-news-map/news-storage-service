package com.newsmap.events;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private Set<TrackedAreaEvent> trackedAreas = new HashSet<>();
}
