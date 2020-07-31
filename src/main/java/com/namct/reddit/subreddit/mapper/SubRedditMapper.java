package com.namct.reddit.subreddit.mapper;

import java.util.List;

import com.namct.reddit.post.PostModel;
import com.namct.reddit.subreddit.SubRedditModel;
import com.namct.reddit.subreddit.dto.SubRedditDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapstruct Mapper and Spring should identify it as a component
*/
@Mapper(componentModel = "spring")
public interface SubRedditMapper {
    @Mapping(target = "postList", expression = "java(getPostCount(subRedditModel.getPosts()))")
    SubRedditDto mapToSubRedditDto(SubRedditModel subRedditModel);

    default Integer getPostCount(List<PostModel> postList){
        return postList.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubRedditModel mapToSubRedditModel(SubRedditDto subRedditDto);
}