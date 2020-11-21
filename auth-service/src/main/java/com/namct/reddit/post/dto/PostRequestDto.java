package com.namct.reddit.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long postId;
    private String title;
    private String description;
    private String url;
    private String subRedditName;
}