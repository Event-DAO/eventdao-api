package com.eventdao.api.dto.response.event;

import com.eventdao.api.entity.constant.EventType;
import com.eventdao.api.entity.event.EventLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private Long id;
    private EventType eventType;
    private String eventName;
    private String shortDescription;
    private String description;
    private ImageResponse images;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private EventLocationResponse eventLocation;
    private boolean isActive;

}
