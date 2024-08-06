package com.example.stockexchange.service;

import com.example.stockexchange.domain.bo.AuthBo;
import com.example.stockexchange.domain.bo.UserBo;

public interface AuthenticationService {

    AuthBo login(UserBo userBo);
}
