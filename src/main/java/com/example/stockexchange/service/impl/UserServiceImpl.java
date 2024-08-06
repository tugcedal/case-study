package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.UserBo;
import com.example.stockexchange.domain.mapper.UserMapper;
import com.example.stockexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserPersistenceServiceImpl userPersistenceService;
    private final UserMapper userMapper;

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.info("User Service load user with name: {}", username);

        UserBo userBo = userPersistenceService.retrieveUser(username);
        return userMapper.convertToUserDetails(userBo);
    }

    @Override
    public UserBo registerUser(UserBo userBo) {
        LOGGER.info("User Service register user");
        return userPersistenceService.saveUser(userBo);
    }
}
