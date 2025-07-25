package com.example.education_system.user.mapper;

import com.example.education_system.auth.dto.UserRequestDTO;
import com.example.education_system.user.dto.UserResponseDto;
import com.example.education_system.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//The @Mapper annotation tells MapStruct to generate the implementation of this interface.
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntity(UserRequestDTO userUserRequestDTO);

    @Mapping(source = "imageFile.url", target = "profileUrl")
    UserResponseDto toResponseDto(UserEntity entity);
}

