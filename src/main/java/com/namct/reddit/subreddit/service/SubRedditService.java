package com.namct.reddit.subreddit.service;

import java.util.Optional;

import com.namct.reddit.subreddit.mapper.SubRedditMapper;
import com.namct.reddit.auth.service.LoginService;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.subreddit.SubRedditRepository;
import com.namct.reddit.subreddit.dto.SubRedditDto;
import com.namct.reddit.subreddit.exceptions.SubRedditException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
public class SubRedditService {
    private SubRedditRepository subRedditRepository;
    private SubRedditMapper subRedditMapper;

    public SubRedditDto getSubReddit(Long id) {
        SubRedditModel result = subRedditRepository.findById(id)
                .orElseThrow(() -> new SubRedditException("Subreddit not found with id -" + id));

        return subRedditMapper.mapToSubRedditDto(result);
    }

    @Transactional
    public SubRedditDto create(SubRedditDto subRedditDto) {
        SubRedditModel subReddit = subRedditRepository.save(subRedditMapper.mapToSubRedditModel(subRedditDto));
        subRedditDto.setId(subReddit.getId());

        return subRedditDto;
    }

    // private SubRedditModel mapToSubRedditModel(SubRedditDto subRedditDto) {

    //     return SubRedditModel.builder().name("r/" + subRedditDto.getName()).description(subRedditDto.getDescription())
    //             .user(loginService.getLoggedInUser()).createdAt(now()).build();
    // }

    // private SubRedditDto mapToSubRedditDto(SubRedditModel subReddit) {
    //     return SubRedditDto.builder().id(subReddit.getId()).name(subReddit.getName())
    //             .postCount(subReddit.getPosts().size()).build();
    // }

    // TODO: Update isExist abstraction
    private boolean isSubRedditExist(String subRedditName) {
        return false;
    }
}