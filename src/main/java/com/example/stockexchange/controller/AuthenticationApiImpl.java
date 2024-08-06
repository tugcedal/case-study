package com.example.stockexchange.controller;

import com.company.project.api.AuthenticationApi;
import com.company.project.model.AuthResponse;
import com.company.project.model.User;
import com.company.project.model.UserREQ;
import com.example.stockexchange.domain.bo.AuthBo;
import com.example.stockexchange.domain.bo.UserBo;
import com.example.stockexchange.domain.mapper.AuthMapper;
import com.example.stockexchange.domain.mapper.UserMapper;
import com.example.stockexchange.service.AuthenticationService;
import com.example.stockexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationApiImpl extends BaseController implements AuthenticationApi {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;


    private static final Logger LOGGER = LogManager.getLogger(AuthenticationApiImpl.class);

    @Override
    public ResponseEntity<AuthResponse> login(UserREQ body) {
        LOGGER.info("Login request received for user name: {}", body.getData().getUsername());

        AuthBo authBo = authenticationService.login(userMapper.convert(body.getData()));
        return ResponseEntity.ok(authMapper.convert(authBo));
    }

    @Override
    public ResponseEntity<User> register(UserREQ body) {
        LOGGER.info("Register request received for user name: {}", body.getData().getUsername());

        User user = body.getData();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserBo userBo = userService.registerUser(userMapper.convert(user));
        return ResponseEntity.ok(userMapper.convert(userBo));
    }
}
