package com.namct.reddit.vote.repository;

import java.util.List;
import java.util.Optional;

import com.namct.reddit.post.PostModel;
import com.namct.reddit.users.UserModel;
import com.namct.reddit.vote.model.VoteModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteModel, Long> {
    List<VoteModel> findAllByUser(UserModel user);

	Optional<VoteModel> findByUserAndPost(UserModel currentLoggedInUser, PostModel post);
}