package com.namct.reddit.vote.service;

import com.namct.reddit.vote.repository.VoteRepository;
import com.namct.reddit.auth.service.LoginService;
import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.post.PostModel;
import com.namct.reddit.post.PostRepository;
import com.namct.reddit.vote.dto.VoteDto;
import com.namct.reddit.vote.model.VoteModel;

import static com.namct.reddit.vote.VoteType.UPVOTE;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

    private VoteRepository voteRepository;
    private PostRepository postRepository;
    private LoginService loginService;

    public void doVote(VoteDto voteDto) {
        PostModel post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new BaseException("Post not found"));

        if (isVoteNotDuplicated(voteDto, post)) {
            throw new BaseException("Already " + voteDto.getVoteType() + " for this post");
        }

        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVoteModel(voteDto, post));
        postRepository.save(post);
    }

    private VoteModel mapToVoteModel(VoteDto voteDto, PostModel post) {
        return VoteModel.builder().voteType(voteDto.getVoteType()).post(post)
                .user(loginService.getCurrentLoggedInUser()).build();
    }

    private boolean isVoteNotDuplicated(VoteDto voteDto, PostModel post) {
        Optional<VoteModel> vote =
                voteRepository.findByUserAndPost(loginService.getCurrentLoggedInUser(), post);

        return vote.isPresent() && vote.get().getVoteType().equals(voteDto.getVoteType());
    }

}
