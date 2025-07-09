package com.newsmap.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponseDto {
    private String id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
}
