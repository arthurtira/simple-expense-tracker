package com.arthurtira.tracker.mappers;

import com.arthurtira.tracker.dto.UserEntityDto;
import com.arthurtira.tracker.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity fromDto(UserEntityDto userEntityDto);

    UserEntityDto toDto(UserEntity userEntity);
}
