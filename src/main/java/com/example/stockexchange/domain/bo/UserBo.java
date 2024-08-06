package com.example.stockexchange.domain.bo;

import lombok.Data;

@Data
public class UserBo extends BusinessObject {

    private String username;
    private String password;
}
