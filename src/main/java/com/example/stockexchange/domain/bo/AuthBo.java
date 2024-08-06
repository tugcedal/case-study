package com.example.stockexchange.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthBo extends BusinessObject {
    private String token;
    private String username;
    private String password;
}
