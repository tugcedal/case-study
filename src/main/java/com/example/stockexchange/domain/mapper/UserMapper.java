package com.example.stockexchange.domain.mapper;

import com.company.project.model.User;
import com.example.stockexchange.domain.bo.UserBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.example.stockexchange.utils.Constants.GRANTED_AUTHORITIES;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserMapper {

    public abstract UserBo convert(User user);

    @Mapping(target = "password", ignore = true)
    public abstract User convert(UserBo userBo);

    public org.springframework.security.core.userdetails.User convertToUserDetails(UserBo userBo) {
        return new org.springframework.security.core.userdetails.User(userBo.getUsername(), userBo.getPassword(), GRANTED_AUTHORITIES);
    }
}
