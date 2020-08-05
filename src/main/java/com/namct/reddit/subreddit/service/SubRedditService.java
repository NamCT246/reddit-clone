package com.namct.reddit.subreddit.service;

import com.namct.reddit.auth.service.LoginService;
import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.subreddit.SubRedditRepository;
import com.namct.reddit.subreddit.dto.SubRedditDto;
import com.namct.reddit.subreddit.exceptions.SubRedditException;
import com.namct.reddit.subreddit.mapper.SubRedditMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubRedditService {
    private SubRedditRepository subRedditRepository;
    private LoginService loginService;
    private SubRedditMapper subRedditMapper;

    public SubRedditDto getSubReddit(Long id) {
        SubRedditModel result = subRedditRepository.findById(id)
                .orElseThrow(() -> new SubRedditException("Subreddit not found with id -" + id));

        return subRedditMapper.mapToSubRedditDto(result);
    }

    @Transactional
    public SubRedditDto create(SubRedditDto subRedditDto) {
        if (subRedditRepository.existsByName(subRedditDto.getName())) {
            throw new BaseException("Subreddit already exist");
        }
        SubRedditModel subReddit = subRedditRepository.save(subRedditMapper.mapToSubRedditModel(subRedditDto,  loginService.getCurrentLoggedInUser())); 
        subRedditDto.setId(subReddit.getId());

        return subRedditDto;
    }
}