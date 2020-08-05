package com.namct.reddit.comment.mapper;

import com.namct.reddit.comment.dto.CommentDto;
import com.namct.reddit.comment.model.CommentModel;
import com.namct.reddit.post.PostModel;
import com.namct.reddit.users.UserModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "commentId", expression = "java(commentDto.getId())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    CommentModel mapToCommentModel(CommentDto commentDto, PostModel post, UserModel user);

    @Mapping(target = "id", source = "commentId")
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToCommentDto(CommentModel comment);
}