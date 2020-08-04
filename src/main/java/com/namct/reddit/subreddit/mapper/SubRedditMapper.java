package com.namct.reddit.subreddit.mapper;

import java.util.List;

import com.namct.reddit.post.PostModel;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.subreddit.dto.SubRedditDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {

    @Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
    SubRedditDto mapToSubRedditDto(SubRedditModel subreddit);

    default Integer mapPosts(List<PostModel> postCount) {
        return postCount.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubRedditModel mapToSubRedditModel(SubRedditDto subredditDto);
}