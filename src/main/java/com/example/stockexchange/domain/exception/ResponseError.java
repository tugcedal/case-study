package com.example.stockexchange.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
    String errorType;
    String detail;
}
