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
public class ExtractNewsLocationEvent {
    private Long mapNewsItemId;
    private String title;
    private String description;
    private Set<TrackedAreaEvent> trackedAreas = new HashSet<>();
}
