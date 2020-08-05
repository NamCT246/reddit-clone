package com.namct.reddit.vote.dto;

import com.namct.reddit.vote.VoteType;

import lombok.Data;

@Data
public class VoteDto {
    private Long postId;
    private VoteType voteType;
}