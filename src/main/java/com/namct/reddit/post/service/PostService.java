package com.namct.reddit.post.service;

import java.util.List;
import java.util.Optional;

import com.namct.reddit.auth.service.LoginService;
import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.post.PostModel;
import com.namct.reddit.post.PostRepository;
import com.namct.reddit.post.dto.PostRequestDto;
import com.namct.reddit.post.dto.PostResponseDto;
import com.namct.reddit.post.mapper.PostMapper;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.subreddit.SubRedditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
public class PostService {

	private SubRedditRepository subRedditRepository;
	private PostRepository postRepository;
	private PostMapper postMapper;
	private LoginService loginService;

	@Transactional(readOnly = true)
	public PostResponseDto getPost(Long id) {
		PostModel post = postRepository.findById(id)
				.orElseThrow(() -> new BaseException("Post not found, deleted or have never been created -" + id));

		return postMapper.mapToPostResponseDto(post);
	}

	@Transactional
	public void create(PostRequestDto postRequestDto) {
		SubRedditModel subReddit = subRedditRepository.findByName(postRequestDto.getSubRedditName()).orElseThrow(() -> new BaseException("Post need to be in a sub reddit"));

		postRepository.save(postMapper.mapToPostModel(postRequestDto, subReddit, loginService.getCurrentLoggedInUser()));
	}

	public List<PostResponseDto> getPostsByUser(Long id) {
		return null;
	}

	public List<PostResponseDto> getPostsBySubReddit(Long id) {
		return null;
	}

}