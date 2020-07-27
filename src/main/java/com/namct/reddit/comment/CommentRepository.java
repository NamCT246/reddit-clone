package com.namct.reddit.comment;

import java.util.List;

import com.namct.reddit.post.PostModel;
import com.namct.reddit.users.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    List<CommentModel> findAllByUser(UserModel user);
    List<CommentModel> findByPost(PostModel post);
}