package com.namct.reddit.subreddit.controller;

import javax.validation.Valid;

import com.namct.reddit.subreddit.dto.SubRedditDto;
import com.namct.reddit.subreddit.service.SubRedditService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subreddit")
public class SubRedditController {
    private SubRedditService subRedditService;

    @GetMapping("/{id}")
    public SubRedditDto getSubReddit(@PathVariable Long id) {
        return subRedditService.getSubReddit(id);
    }

    @PostMapping("/create")
    public SubRedditDto create(@RequestBody @Valid SubRedditDto subredditDto) {
        return subRedditService.create(subredditDto);
    }

    @GetMapping("/featured")
    public SubRedditDto getFeaturedSubReddit() {
        return subRedditService.getFeaturedSubReddit();
    }
}
