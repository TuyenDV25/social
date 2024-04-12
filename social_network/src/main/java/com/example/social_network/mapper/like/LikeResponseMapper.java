package com.example.social_network.mapper.like;

import org.mapstruct.Mapper;

import com.example.social_network.dto.like.LikeResDto;
import com.example.social_network.entity.Like;
import com.example.social_network.mapper.MapperI;

@Mapper(componentModel = "spring")
public interface LikeResponseMapper extends MapperI<Like, LikeResDto> {

}
