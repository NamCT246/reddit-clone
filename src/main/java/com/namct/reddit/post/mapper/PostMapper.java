package com.namct.reddit.post.mapper;

import com.namct.reddit.post.PostModel;
import com.namct.reddit.post.dto.PostRequestDto;
import com.namct.reddit.post.dto.PostResponseDto;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.users.UserModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Mapping(target = "id", source="postId")
    @Mapping(target = "userName", source="user.username")
    @Mapping(target = "subRedditName", source = "subReddit.name")
    // @Mapping(target = "commentCount", expression = "java(getCommentCount())")
    // @Mapping(target = "duration", expression = "java(getDuration())")
    // @Mapping(target = "upVote", expression = "java(isUpVoted(post))")
    // @Mapping(target = "downVote", expression = "java(isDownVoted(post))")
    public abstract PostResponseDto mapToPostResponseDto(PostModel post);

    @Mapping(target = "description", source = "postRequestDto.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "subReddit", source = "subRedditModel")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    public abstract PostModel mapToPostModel(PostRequestDto postRequestDto, SubRedditModel subRedditModel,
            UserModel user);
}