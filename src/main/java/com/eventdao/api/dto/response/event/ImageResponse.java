package com.eventdao.api.dto.response.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private String bigImageUrl;
    private String midImageUrl;
    private String smallImageUrl;
}
