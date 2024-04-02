package com.example.social_network.mapper.image;

import org.mapstruct.Mapper;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.entity.Image;
import com.example.social_network.mapper.MapperI;

@Mapper
public interface ImageResponseMapper extends MapperI<Image, ImageResDto> {
	

}
