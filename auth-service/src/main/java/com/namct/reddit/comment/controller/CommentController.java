package com.namct.reddit.comment.controller;

import java.util.List;

import com.namct.reddit.comment.dto.CommentDto;
import com.namct.reddit.comment.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CommentDto commentDto) {
        commentService.create(commentDto);
        return new ResponseEntity<>("Comment posted", HttpStatus.CREATED);
    }

    @GetMapping("by-post/{postId}")
    public List<CommentDto> getPostsByUser(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
}