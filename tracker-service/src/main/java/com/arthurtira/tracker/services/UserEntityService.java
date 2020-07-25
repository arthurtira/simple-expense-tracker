package com.arthurtira.tracker.services;

import com.arthurtira.tracker.dto.UserEntityDto;
import com.arthurtira.tracker.model.UserEntity;

import java.security.Principal;
import java.util.Optional;

public interface UserEntityService {
    UserEntity findUserEntityByUsername(String username);
    Optional<UserEntity> findUserByPrincipal(Principal principal);
    UserEntityDto createAccount(UserEntityDto userEntityDtoRequest);
}
