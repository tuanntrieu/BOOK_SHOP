package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.request.RegisterRequestDto;
import com.example.projectbase.domain.dto.response.UserDto;
import com.example.projectbase.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequestDto requestDto);

    @Mappings({
            @Mapping(target = "roleName", source = "user.role.name"),
            @Mapping(target = "customerId", source = "customer.id")
    })
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> user);

}
