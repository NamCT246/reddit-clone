package com.namct.reddit.post.mapper;

import com.namct.reddit.auth.service.LoginService;
import com.namct.reddit.comment.repository.CommentRepository;
import com.namct.reddit.post.PostModel;
import com.namct.reddit.post.dto.PostRequestDto;
import com.namct.reddit.post.dto.PostResponseDto;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.users.UserModel;
import com.namct.reddit.vote.VoteType;
import com.namct.reddit.vote.model.VoteModel;
import com.namct.reddit.vote.repository.VoteRepository;
import  com.namct.reddit.vote.VoteType;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private LoginService loginService;

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "subRedditName", source = "subReddit.name")
    @Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
    // @Mapping(target = "duration", expression = "java(getDuration())")
    @Mapping(target = "upVote", expression = "java(isUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isDownVoted(post))")
    public abstract PostResponseDto mapToPostResponseDto(PostModel post);

    @Mapping(target = "description", source = "postRequestDto.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "subReddit", source = "subRedditModel")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    public abstract PostModel mapToPostModel(PostRequestDto postRequestDto, SubRedditModel subRedditModel,
            UserModel user);

    Integer getCommentCount(PostModel post) {
        return commentRepository.findByPost(post).size();
    }

    boolean isUpVoted(PostModel post) {
        return isPostUpvoted(post, VoteType.UPVOTE);
    }

    boolean isDownVoted(PostModel post) {
        return isPostUpvoted(post, VoteType.DOWNVOTE);
    }

    private boolean isPostUpvoted(PostModel post, VoteType voteType) {
        if (loginService.isLoggedIn()) {
            Optional<VoteModel> votes = voteRepository.findByUserAndPost(loginService.getCurrentLoggedInUser(), post);

            return votes.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }

        return false;
    }
}