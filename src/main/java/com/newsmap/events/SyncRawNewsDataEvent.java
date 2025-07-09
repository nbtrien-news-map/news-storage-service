package com.newsmap.events;

import lombok.*;

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
}
