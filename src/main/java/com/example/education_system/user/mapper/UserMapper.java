package com.example.education_system.user.mapper;

import com.example.education_system.user.dto.RegistrationDto;
import com.example.education_system.user.dto.UserResponseDto;
import com.example.education_system.user.entity.UserEntity;
import org.mapstruct.Mapper;

//The @Mapper annotation tells MapStruct to generate the implementation of this interface.
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(RegistrationDto userRegistrationDTO);
    UserResponseDto toResponseDto(UserEntity entity);
}

