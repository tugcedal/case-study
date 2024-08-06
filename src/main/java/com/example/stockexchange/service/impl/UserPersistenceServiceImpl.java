package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.UserBo;
import com.example.stockexchange.domain.entity.UserEntity;
import com.example.stockexchange.domain.exception.ResourceAlreadyExists;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import com.example.stockexchange.domain.mapper.UserEntityMapper;
import com.example.stockexchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.stockexchange.utils.Constants.Error.USER_ALREADY_EXISTS;
import static com.example.stockexchange.utils.Constants.Error.USER_NOT_FOUND;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class UserPersistenceServiceImpl {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    private static final Logger LOGGER = LogManager.getLogger(UserPersistenceServiceImpl.class);

    public UserBo retrieveUser(String userName) {
        LOGGER.info("User Persistence service retrieve user");

        UserEntity user = userRepository.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException(format(USER_NOT_FOUND, userName)));
        return userEntityMapper.convert(user);
    }

    public UserBo saveUser(UserBo userBo) {
        LOGGER.info("User Persistence service save user");

        Optional<UserEntity> user = userRepository.findByUsername(userBo.getUsername());

        if (user.isPresent()) {
            throw new ResourceAlreadyExists(format(USER_ALREADY_EXISTS, userBo.getUsername()));
        }
        UserEntity userEntity = userRepository.save(userEntityMapper.convert(userBo));
        return userEntityMapper.convert(userEntity);
    }
}
