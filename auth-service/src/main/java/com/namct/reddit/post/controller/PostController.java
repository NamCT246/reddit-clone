package com.namct.reddit.post.controller;

import java.util.List;

import com.namct.reddit.post.dto.PostRequestDto;
import com.namct.reddit.post.dto.PostResponseDto;
import com.namct.reddit.post.service.PostService;

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
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody PostRequestDto postDto) {
        postService.create(postDto);
        return new ResponseEntity<>("Successfully created post", HttpStatus.CREATED);
    }

    @GetMapping("by-user/{username}")
    public List<PostResponseDto> getPostsByUser(@PathVariable String username) {
        return postService.getPostsByUser(username);
    }

    @GetMapping("by-subreddit/{subRedditId}")
    public List<PostResponseDto> getPostsBySubReddit(@PathVariable Long subRedditId) {
        return postService.getPostsBySubReddit(subRedditId);
    }
}
