package com.arthurtira.tracker.mappers;

import com.arthurtira.tracker.dto.UserEntityDto;
import com.arthurtira.tracker.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mappings({
            @Mapping(source = "email" , target = "email")
    })
    UserEntity fromDto(UserEntityDto userEntityDto);

    UserEntityDto toDto(UserEntity userEntity);
}
