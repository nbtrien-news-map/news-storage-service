package com.newsmap.events;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrackedAreaEvent {
    private Long id;
    private String osmType;
    private Long osmId;
}
