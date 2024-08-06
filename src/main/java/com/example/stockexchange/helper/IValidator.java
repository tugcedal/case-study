package com.example.stockexchange.helper;

import com.example.stockexchange.domain.bo.BusinessObject;

public interface IValidator<T extends BusinessObject> {
    void validateCreate(T t);
}
