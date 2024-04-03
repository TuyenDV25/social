package com.example.social_network.mapper.user;

import org.mapstruct.Mapper;

import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.mapper.MapperI;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperI<UserInfo, UserInforResDto> {

}
