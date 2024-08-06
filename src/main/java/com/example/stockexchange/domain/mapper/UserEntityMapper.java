package com.example.stockexchange.domain.mapper;

import com.example.stockexchange.domain.bo.UserBo;
import com.example.stockexchange.domain.entity.UserEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserEntityMapper {

    public abstract UserBo convert(UserEntity userEntity);

    public abstract UserEntity convert(UserBo userBo);

}
