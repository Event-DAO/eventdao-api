package com.eventdao.api.dto.response.auth;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
public class ErrorResponse {

    public static final ErrorResponse FORBIDDEN = ErrorResponse.builder().code("999").message("").build();
    public static final ErrorResponse UNAUTHORIZED = ErrorResponse.builder().code("998").message("Full authentication is required to access this resource").build();
    public static final ErrorResponse GENERIC = ErrorResponse.builder().code("0").message("Exception Occured when execution.Please contact with the development team.").build();

    private final String code;
    private final String message;
    private final Timestamp timestamp = Timestamp.from(Instant.now());
}
