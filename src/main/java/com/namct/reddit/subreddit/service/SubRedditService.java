package com.namct.reddit.subreddit.service;

import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.subreddit.SubRedditRepository;
import com.namct.reddit.subreddit.dto.SubRedditDto;
import com.namct.reddit.subreddit.exceptions.SubRedditException;
import com.namct.reddit.subreddit.mapper.SubRedditMapper;

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

    // TODO: Update isExist abstraction
    private boolean isSubRedditExist(String subRedditName) {
        return false;
    }
}