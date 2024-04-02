package com.example.social_network.mapper.post;

import org.mapstruct.Mapper;

import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.entity.Post;
import com.example.social_network.mapper.MapperI;

@Mapper
public interface PostRequestMapper extends MapperI<Post, PostPostReqDto>{

}
