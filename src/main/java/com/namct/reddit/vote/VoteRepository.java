package com.namct.reddit.vote;

import java.util.List;

import com.namct.reddit.users.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteModel, Long> {
    List<VoteModel> findAllByUser(UserModel user);
}