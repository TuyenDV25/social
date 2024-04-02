package com.example.social_network.mapper.comment;

import org.mapstruct.Mapper;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.entity.Comment;
import com.example.social_network.mapper.MapperI;

@Mapper
public interface CommentResponseMapper extends MapperI<Comment, CommentResDto> {

}
