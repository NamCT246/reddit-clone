package com.namct.reddit.vote.controller;

import com.namct.reddit.vote.dto.VoteDto;
import com.namct.reddit.vote.service.VoteService;

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
@RequestMapping("/api/vote/")
@AllArgsConstructor
public class VoteController {

    private VoteService voteService;

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody VoteDto voteDto) {
        voteService.doVote(voteDto);
        return new ResponseEntity<>("Voted", HttpStatus.CREATED);
    }

}