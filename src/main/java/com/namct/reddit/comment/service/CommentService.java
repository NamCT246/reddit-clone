package com.namct.reddit.comment.service;

import java.util.List;

import com.namct.reddit.auth.service.LoginService;
import com.namct.reddit.comment.dto.CommentDto;
import com.namct.reddit.comment.mapper.CommentMapper;
import com.namct.reddit.comment.model.CommentModel;
import com.namct.reddit.comment.repository.CommentRepository;
import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.post.PostModel;
import com.namct.reddit.post.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import static java.util.stream.Collectors.toList;;

@Service
@AllArgsConstructor
public class CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private LoginService loginService;

    @Transactional
    public void create(CommentDto commentDto) {

        PostModel post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new BaseException("Post does not exist"));

        CommentModel comment = commentMapper.mapToCommentModel(commentDto, post, loginService.getCurrentLoggedInUser());

        commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException("Post does not exist"));

        return commentRepository.findByPost(post)
        .stream()
        .map(
            commentMapper::mapToCommentDto
            )
        .collect(toList());
        
    }
}