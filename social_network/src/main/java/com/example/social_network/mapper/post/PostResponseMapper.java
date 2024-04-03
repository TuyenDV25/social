package com.example.social_network.mapper.post;

import org.mapstruct.Mapper;

import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.entity.Post;
import com.example.social_network.mapper.MapperI;

@Mapper(componentModel = "spring")
public interface PostResponseMapper extends MapperI<Post, PostPostResDto> {

}
