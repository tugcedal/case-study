package com.example.stockexchange.domain.mapper;

import com.company.project.model.AuthResponse;
import com.example.stockexchange.domain.bo.AuthBo;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class AuthMapper {

    public abstract AuthResponse convert(AuthBo authBo);

    public abstract AuthBo convert(AuthResponse authBo);

}
