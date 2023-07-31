package com.eventdao.api.dto.response.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventLocationResponse {

    private String address;
    private String city;
    private String state;
    private String country;
    private String areaDetails;
    private int capacity;
    private String facilities;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private ImageResponse images;
}
