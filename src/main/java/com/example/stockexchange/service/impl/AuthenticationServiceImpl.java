package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.AuthBo;
import com.example.stockexchange.domain.bo.UserBo;
import com.example.stockexchange.service.AuthenticationService;
import com.example.stockexchange.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationServiceImpl.class);

    @Override
    public AuthBo login(UserBo userBo) {
        LOGGER.info("Authentication service login");

        Authentication authentication = authenticationManager.authenticate(buildAuthenticationToken(userBo));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        return buildAuthBo(jwt, userDetails);
    }

    private static UsernamePasswordAuthenticationToken buildAuthenticationToken(UserBo userBo) {
        return new UsernamePasswordAuthenticationToken(userBo.getUsername(), userBo.getPassword());
    }

    private static AuthBo buildAuthBo(String jwt, User userDetails) {
        return new AuthBo(jwt, userDetails.getUsername(), null);
    }
}
