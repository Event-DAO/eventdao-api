package com.eventdao.api.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
public class GenericResponse {

    public static final GenericResponse GENERIC = GenericResponse.builder().message("Success!").build();

    private final String message;
    private final Timestamp timestamp = Timestamp.from(Instant.now());
}
