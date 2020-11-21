package com.namct.reddit.subreddit;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRedditRepository extends JpaRepository<SubRedditModel, Long> {
    Optional<SubRedditModel> findByName(String subRedditName);

	boolean existsByName(String name);
}