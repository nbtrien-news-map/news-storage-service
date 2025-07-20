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
public class SyncRawNewsDataEvent {
    private Integer providerId;
    private String title;
    private String description;
    private String link;
    private String publishedAt;
    private Set<TrackedAreaEvent> trackedAreas = new HashSet<>();
}
