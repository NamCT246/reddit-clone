package com.namct.reddit.comment.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long postId;
    private Instant createdAt;
    private String text;
    private String userName;
}