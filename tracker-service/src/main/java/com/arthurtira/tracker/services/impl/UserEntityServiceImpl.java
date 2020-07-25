package com.arthurtira.tracker.services.impl;

import com.arthurtira.tracker.dto.UserEntityDto;
import com.arthurtira.tracker.mappers.UserEntityMapper;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.repository.UserEntityRepository;
import com.arthurtira.tracker.services.UserEntityService;
import com.arthurtira.tracker.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service@Slf4j
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository repository;
    private final JwtUtil jwtUtil;
    private final UserEntityMapper userEntityMapper;

    public UserEntityServiceImpl(UserEntityRepository repository, JwtUtil jwtUtil, UserEntityMapper mapper) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
        this.userEntityMapper = mapper;
    }

    @Override
    public UserEntity findUserEntityByUsername(String username) {
        Optional<UserEntity> userEntityOptional = repository.findUserEntityByUsername(username);
        if(userEntityOptional.isPresent()) {
            return userEntityOptional.get();
        }

       return null;
    }

    @Override
    public Optional<UserEntity> findUserByPrincipal(Principal principal) {
        log.debug("Principal {} " + principal.getName());
        Optional<UserEntity> userEntityOptional = repository.findUserEntityByUsername(principal.getName());
        if(userEntityOptional.isPresent()) {
            return Optional.of(userEntityOptional.get());
        }
        return Optional.empty();
    }

    @Override
    public UserEntityDto createAccount(UserEntityDto userEntityDtoRequest) {
        UserEntity userEntity = this.userEntityMapper.fromDto(userEntityDtoRequest);
        log.debug("UserEntity {} " + userEntity);
        return userEntityMapper.toDto(repository.save(userEntity));

    }


}
