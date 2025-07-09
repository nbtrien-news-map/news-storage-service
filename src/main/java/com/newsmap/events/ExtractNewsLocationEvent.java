package com.newsmap.events;

import lombok.*;

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
}
