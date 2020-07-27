package com.namct.reddit.post;

import java.util.List;

import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.users.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
    List<PostModel> findByUser(UserModel user);
    List<PostModel> findBySubreddit(SubRedditModel subreddit);
}